package org.scriptonbasestar.kcext.idp.naver

import org.keycloak.broker.oidc.OAuth2IdentityProviderConfig
import org.keycloak.models.IdentityProviderModel

class NaverIdentityProviderConfig : OAuth2IdentityProviderConfig {
    constructor() : super() {
        this.alias = NaverConstant.providerId
    }
    constructor(model: IdentityProviderModel) : super(model) {
        this.alias = NaverConstant.providerId
    }

    var scopes: String = NaverConstant.defaultScope
}
