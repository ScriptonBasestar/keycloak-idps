package org.scriptonbasestar.kcext.idp.naver

import org.keycloak.models.IdentityProviderMapperModel
import org.keycloak.models.KeycloakSession
import org.keycloak.models.RealmModel.IdentityProviderRemovedEvent
import org.keycloak.models.RealmModel.IdentityProviderUpdatedEvent
import org.keycloak.models.utils.RepresentationToModel
import org.keycloak.provider.ProviderEvent
import org.keycloak.provider.ProviderEventListener
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation

class NaverIdentityEventListener(
    private val session: KeycloakSession
) : ProviderEventListener {
    override fun onEvent(event: ProviderEvent) {
//        IdentityProviderUpdatedEvent
//        IdentityProviderRemovedEvent
        when (event) {
            is IdentityProviderUpdatedEvent -> {
                println("NaverIdentityEventListener.onEvent $event")
                val mapper = IdentityProviderMapperRepresentation().apply {
                    name = "naver-user-attribute-mapper"
                    identityProviderAlias = NaverConstant.providerId
                    identityProviderMapper = "user-attribute-mapper"
                    config = mapOf(
                        "user.attribute" to "naver_id",
                        "attribute.name" to "naver_id",
                        "jsonType.label" to "String",
                        "userinfo.token.claim" to "true",
                        "id.token.claim" to "true",
                        "access.token.claim" to "true",
                        "claim.name" to "naver_id",
                        "usermodel.clientRoleMapping.clientId" to "account",
                        "usermodel.clientRoleMapping.role" to "manage-account",
                        "usermodel.realmRoleMapping.role" to "manage-account"
                    )
                }
                var model: IdentityProviderMapperModel = RepresentationToModel.toModel(mapper)
                model = event.realm.addIdentityProviderMapper(model)
            }
            is IdentityProviderRemovedEvent -> {
                println("NaverIdentityEventListener.onEvent $event")
            }
        }
    }
}
