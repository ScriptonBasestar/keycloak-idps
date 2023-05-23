package org.scriptonbasestar.kcext.idp.naver

import org.keycloak.Config
import org.keycloak.broker.provider.AbstractIdentityProviderFactory
import org.keycloak.broker.social.SocialIdentityProviderFactory
import org.keycloak.models.IdentityProviderModel
import org.keycloak.models.KeycloakSession
import org.keycloak.models.KeycloakSessionFactory
import org.keycloak.provider.ProviderConfigProperty
import java.io.InputStream

class NaverIdentityProviderFactory :
    AbstractIdentityProviderFactory<NaverIdentityProvider>(),
    SocialIdentityProviderFactory<NaverIdentityProvider> {

    private lateinit var sessionFactory: KeycloakSessionFactory
    private lateinit var identityEventListener: NaverIdentityEventListener

    override fun init(config: Config.Scope) {
        super.init(config)
    }

    override fun postInit(factory: KeycloakSessionFactory) {
        this.identityEventListener = NaverIdentityEventListener(factory.create())
        this.sessionFactory = factory
        factory.register(this.identityEventListener)
        super.postInit(factory)
    }

    override fun close() {
        if (this::sessionFactory.isInitialized) {
            sessionFactory.unregister(this.identityEventListener)
        }
        super.close()
    }

    override fun create(session: KeycloakSession, model: IdentityProviderModel): NaverIdentityProvider {
        return NaverIdentityProvider(session, NaverIdentityProviderConfig(model))
    }

    override fun parseConfig(session: KeycloakSession, inputStream: InputStream): MutableMap<String, String> {
        return super.parseConfig(session, inputStream)
    }

    override fun getId(): String = NaverConstant.providerId

    override fun getName(): String = NaverConstant.providerName

    override fun createConfig(): IdentityProviderModel = NaverIdentityProviderConfig()

    override fun getConfigMetadata(): MutableList<ProviderConfigProperty> = mutableListOf()
}
