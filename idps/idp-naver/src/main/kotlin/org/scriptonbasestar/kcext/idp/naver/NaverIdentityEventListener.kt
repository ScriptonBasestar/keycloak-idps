package org.scriptonbasestar.kcext.idp.naver

import org.keycloak.models.KeycloakSession
import org.keycloak.models.RealmModel.IdentityProviderRemovedEvent
import org.keycloak.models.RealmModel.IdentityProviderUpdatedEvent
import org.keycloak.provider.ProviderEvent
import org.keycloak.provider.ProviderEventListener

class NaverIdentityEventListener(
    private val session: KeycloakSession,
): ProviderEventListener {
    override fun onEvent(event: ProviderEvent) {
//        IdentityProviderUpdatedEvent
//        IdentityProviderRemovedEvent
        when(event){
            is IdentityProviderUpdatedEvent -> {
                println("NaverIdentityEventListener.onEvent $event")
                session.
                session.getProvider()
            }
            is IdentityProviderRemovedEvent -> {
                println("NaverIdentityEventListener.onEvent $event")
            }
        }
    }
}
