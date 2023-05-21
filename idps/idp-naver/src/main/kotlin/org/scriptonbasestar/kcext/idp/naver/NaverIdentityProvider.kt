package org.scriptonbasestar.kcext.idp.naver

import com.fasterxml.jackson.databind.JsonNode
import org.keycloak.broker.oidc.AbstractOAuth2IdentityProvider
import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper
import org.keycloak.broker.provider.BrokeredIdentityContext
import org.keycloak.broker.provider.IdentityBrokerException
import org.keycloak.broker.provider.util.SimpleHttp
import org.keycloak.broker.social.SocialIdentityProvider
import org.keycloak.events.EventBuilder
import org.keycloak.models.KeycloakSession

class NaverIdentityProvider(
    keycloakSession: KeycloakSession,
    config: NaverIdentityProviderConfig
) : AbstractOAuth2IdentityProvider<NaverIdentityProviderConfig>(
    keycloakSession,
    config
),
    SocialIdentityProvider<NaverIdentityProviderConfig> {

    init {
        config.authorizationUrl = NaverConstant.authorizationUrl
        config.tokenUrl = NaverConstant.tokenUrl
        config.userInfoUrl = NaverConstant.userInfoUrl
    }

    override fun supportsExternalExchange(): Boolean = true

    override fun getProfileEndpointForValidation(event: EventBuilder): String = NaverConstant.userInfoUrl

    /**
     * https://developers.naver.com/docs/login/profile/profile.md
     * {
     *   "resultcode": "00",
     *   "message": "success",
     *   "response": {
     *     "email": "openapi@naver.com",
     *     "nickname": "OpenAPI",
     *     "profile_image": "https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif",
     *     "age": "40-49",
     *     "gender": "F",
     *     "id": "32742776",
     *     "name": "오픈 API",
     *     "birthday": "10-01",
     *     "birthyear": "1900",
     *     "mobile": "010-0000-0000"
     *   }
     * }
     */
    override fun extractIdentityFromProfile(event: EventBuilder?, profile: JsonNode): BrokeredIdentityContext {
        val user = BrokeredIdentityContext(profile.get("response").get("id").asText())

        // id, end_id, profile_image, age, gender, nickname, email
        // "{"id":"8746367","enc_id":"d3cf187ac8f7dbc635990c54019e1bf79cfc2c07c4ebab25b51281394c9cd84e","profile_image":"https://ssl.pstatic.net/static/pwe/address/img_profile.png","age":"0-9","gender":"U","nickname":"-","email":"rudora@naver.com"}"
        val email: String = profile.get("response").get("email").asText()

        val nickname: String = profile.get("response").get("nickname").asText()
        val profile_image: String = profile.get("response").get("profile_image").asText()

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

        AbstractJsonUserAttributeMapper.storeUserProfileForMapper(user, profile, config.alias)

        return user
    }

    override fun doGetFederatedIdentity(accessToken: String): BrokeredIdentityContext {
        return try {
            val profile = SimpleHttp.doGet(NaverConstant.userInfoUrl, session)
                .param("access_token", accessToken)
                .asJson()
            extractIdentityFromProfile(null, profile)
        } catch (e: Exception) {
            throw IdentityBrokerException("Could not obtain user profile from naver.", e)
        }
    }

    override fun getDefaultScopes(): String = NaverConstant.defaultScope
}
