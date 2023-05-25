Keycloak Exts
-------------

말 그대로 Keycloak의 확장 기능!!!
Horse とおり Keycloak's Ext!!!

## Keycloak Exts

https://www.keycloak.org/extensions.html

### Idps

Identity Providers

mappers - if custom attributes needed
fork - custom username, password
config - ddd

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


sudo docker run --rm -it --entrypoint bash quay.io/keycloak/keycloak:21.1.1

sed -i '$ d' /opt/keycloak/bin/kc.sh

echo $'echo "\'$JAVA\'" $JAVA_RUN_OPTS' >> /opt/keycloak/bin/kc.sh
echo $'eval exec "\'$JAVA\'" $JAVA_RUN_OPTS' >> /opt/keycloak/bin/kc.sh

bash /opt/keycloak/bin/kc.sh start-dev

'java' -Dkc.config.built=true -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m
-Djava.net.preferIPv4Stack=true
-Dfile.encoding=UTF-8
-Dsun.stdout.encoding=UTF-8
-Dsun.err.encoding=UTF-8
-Dstdout.encoding=UTF-8
-Dstderr.encoding=UTF-8
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.security=ALL-UNNAMED
-Dkc.home.dir='/opt/keycloak/bin/..'
-Djboss.server.config.dir='/opt/keycloak/bin/../conf'
-Djava.util.logging.manager=org.jboss.logmanager.LogManager
-Dquarkus-log-max-startup-records=10000
-cp '/opt/keycloak/bin/../lib/quarkus-run.jar'
io.quarkus.bootstrap.runner.`QuarkusEntryPoint`
--profile=dev start-dev
