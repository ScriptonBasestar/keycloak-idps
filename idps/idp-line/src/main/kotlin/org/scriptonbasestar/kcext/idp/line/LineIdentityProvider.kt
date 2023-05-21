package org.scriptonbasestar.kcext.idp.line

import com.fasterxml.jackson.databind.JsonNode
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider
import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper
import org.keycloak.broker.provider.BrokeredIdentityContext
import org.keycloak.broker.provider.IdentityBrokerException
import org.keycloak.broker.provider.util.SimpleHttp
import org.keycloak.broker.social.SocialIdentityProvider
import org.keycloak.events.EventBuilder
import org.keycloak.models.KeycloakSession
import javax.sound.sampled.Line

class LineIdentityProvider(
    keycloakSession: KeycloakSession,
    config: LineIdentityProviderConfig
) : AbstractOAuth2IdentityProvider<LineIdentityProviderConfig>(
    keycloakSession,
    config
),
    SocialIdentityProvider<LineIdentityProviderConfig> {

        init {
            config.authorizationUrl = LineConstant.authorizationUrl
            config.tokenUrl = LineConstant.tokenUrl
            config.userInfoUrl = LineConstant.userInfoUrl
        }

    override fun supportsExternalExchange(): Boolean =true

    override fun getProfileEndpointForValidation(event: EventBuilder?): String = LineConstant.userInfoUrl

    override fun extractIdentityFromProfile(event: EventBuilder?, profile: JsonNode): BrokeredIdentityContext {
        val user = BrokeredIdentityContext(profile.get("id").asText())

        val email: String = profile.get("kakao_account").get("email").asText()
        val nickname: String = profile.get("properties").get("nickname").asText()
        val profile_image: String = profile.get("properties").get("profile_image").asText()
        val thumbnail_image: String = profile.get("properties").get("thumbnail_image").asText()

        user.idpConfig = config
        user.username = email
//        user.firstName = name
//        user.lastName = name
//        user.nickname = nickname
        user.email = email
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
            val profile = SimpleHttp.doGet(LineConstant.userInfoUrl, session)
                .param("access_token", accessToken)
                .asJson()
            extractIdentityFromProfile(null, profile)
        } catch (e: Exception) {
            throw IdentityBrokerException("Could not obtain user profile from naver.", e)
        }
    }


    override fun getDefaultScopes(): String = LineConstant.defaultScope
}
