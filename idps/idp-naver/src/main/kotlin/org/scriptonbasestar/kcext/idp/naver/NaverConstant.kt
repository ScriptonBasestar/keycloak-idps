package org.scriptonbasestar.kcext.idp.naver

object NaverConstant {
    val providerId = "naver"
    val providerName = "Naver"

    val authorizationUrl = "https://nid.naver.com/oauth2.0/authorize"
    val tokenUrl = "https://nid.naver.com/oauth2.0/token"
    val userInfoUrl = "https://openapi.naver.com/v1/nid/me"

//    val defaultScope = "profile email"
    val defaultScope = ""
}
