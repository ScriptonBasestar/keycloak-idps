package org.scriptonbasestar.kcext.idp.kakao

import com.fasterxml.jackson.databind.JsonNode
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider
import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper
import org.keycloak.broker.provider.BrokeredIdentityContext
import org.keycloak.broker.provider.IdentityBrokerException
import org.keycloak.broker.provider.util.SimpleHttp
import org.keycloak.broker.social.SocialIdentityProvider
import org.keycloak.events.EventBuilder
import org.keycloak.models.KeycloakSession

class KakaoIdentityProvider(
    keycloakSession: KeycloakSession,
    config: KakaoIdentityProviderConfig
) : AbstractOAuth2IdentityProvider<KakaoIdentityProviderConfig>(
    keycloakSession,
    config
),
    SocialIdentityProvider<KakaoIdentityProviderConfig> {

    init {
        config.authorizationUrl = KakaoConstant.authorizationUrl
        config.tokenUrl = KakaoConstant.tokenUrl
        config.userInfoUrl = KakaoConstant.userInfoUrl
    }

    override fun supportsExternalExchange(): Boolean = true
    override fun getProfileEndpointForValidation(event: EventBuilder): String = KakaoConstant.userInfoUrl

    /**
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response
     *
     */
    override fun extractIdentityFromProfile(event: EventBuilder?, profile: JsonNode): BrokeredIdentityContext {
        val user = BrokeredIdentityContext(profile.get("sub").asText())

        val name: String = profile.get("name").asText()
        val profile_image: String = profile.get("picture").asText()

        user.idpConfig = config
//        user.username = email
//        user.firstName = name
//        user.lastName = name
//        user.nickname = nickname
//        user.email = email
        user.idp = this

        // use mapper
//        user.setUserAttribute("nickname", nickname)
//        user.setUserAttribute("avatar_url", profile_image)
//        user.setUserAttribute("thumbnail_url", thumbnail_image)

        AbstractJsonUserAttributeMapper.storeUserProfileForMapper(user, profile, config.alias)

        return user
    }

    override fun doGetFederatedIdentity(accessToken: String): BrokeredIdentityContext {
        return try {
            val profile = SimpleHttp.doGet(KakaoConstant.userInfoUrl, session)
                .param("access_token", accessToken)
                .asJson()
            extractIdentityFromProfile(null, profile)
        } catch (e: Exception) {
            throw IdentityBrokerException("Could not obtain user profile from naver.", e)
        }
    }

    override fun getDefaultScopes(): String = KakaoConstant.defaultScope
}
