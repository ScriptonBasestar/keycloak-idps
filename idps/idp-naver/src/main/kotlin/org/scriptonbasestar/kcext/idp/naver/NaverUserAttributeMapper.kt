package org.scriptonbasestar.kcext.idp.naver

import org.keycloak.broker.oidc.mappers.AbstractJsonUserAttributeMapper
import org.keycloak.broker.provider.BrokeredIdentityContext
import org.keycloak.models.IdentityProviderMapperModel
import org.keycloak.models.KeycloakSession
import org.keycloak.models.RealmModel
import org.keycloak.models.UserModel

class NaverUserAttributeMapper : AbstractJsonUserAttributeMapper() {
    override fun getId(): String = "naver-user-attribute-mapper"

    override fun getCompatibleProviders(): Array<String> = arrayOf(NaverConstant.providerId)

    override fun preprocessFederatedIdentity(
        session: KeycloakSession?,
        realm: RealmModel?,
        mapperModel: IdentityProviderMapperModel?,
        context: BrokeredIdentityContext?
    ) {
        super.preprocessFederatedIdentity(session, realm, mapperModel, context)
    }

    override fun importNewUser(
        session: KeycloakSession?,
        realm: RealmModel?,
        user: UserModel?,
        mapperModel: IdentityProviderMapperModel?,
        context: BrokeredIdentityContext?
    ) {
        super.importNewUser(session, realm, user, mapperModel, context)
    }

    override fun updateBrokeredUser(
        session: KeycloakSession?,
        realm: RealmModel?,
        user: UserModel?,
        mapperModel: IdentityProviderMapperModel?,
        context: BrokeredIdentityContext?
    ) {
        super.updateBrokeredUser(session, realm, user, mapperModel, context)
    }

    override fun updateBrokeredUserLegacy(
        session: KeycloakSession?,
        realm: RealmModel?,
        user: UserModel?,
        mapperModel: IdentityProviderMapperModel?,
        context: BrokeredIdentityContext?
    ) {
        super.updateBrokeredUserLegacy(session, realm, user, mapperModel, context)
    }
}
