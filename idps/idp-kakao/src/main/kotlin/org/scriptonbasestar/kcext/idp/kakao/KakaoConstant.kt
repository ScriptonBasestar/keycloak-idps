package org.scriptonbasestar.kcext.idp.kakao

object KakaoConstant {
    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
    val providerId = "kakao"
    val providerName = "Kakao"

    val authorizationUrl = "https://kauth.kakao.com/oauth/authorize"
    val tokenUrl = "https://kauth.kakao.com/oauth/token"
    val userInfoUrl = "https://kapi.kakao.com/v2/user/me"

    val defaultScope = "profile openid account_email"
}
