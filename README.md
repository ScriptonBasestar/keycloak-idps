Keycloak Exts
-------------

말 그대로 Keycloak의 확장 기능!!!
Horse とおり Keycloak's Ext!!!

## Keycloak Exts

https://www.keycloak.org/extensions.html

### Idps

Identity Providers

* kakao -
* naver - scope
* line

link
* discord https://github.com/wadahiro/keycloak-discord
* apple https://github.com/klausbetz/apple-identity-provider-keycloak

### Themes

https://www.keycloakify.dev/

### Events

* kafka
* mqtt https://github.com/softwarefactory-project/keycloak-event-listener-mqtt
* rabbitmq https://github.com/aznamier/keycloak-event-listener-rabbitmq


docker run --rm -it --entrypoint bash quay.io/keycloak/keycloak:21.1.1
docker run --rm -it quay.io/keycloak/keycloak:21.1.1 start-dev
