#!/bin/bash

docker run -d  –name keycloak -p 8888:8080 -e KEYCLOAK_USER=spring -e KEYCLOAK_PASSWORD=spring123 jboss/keycloak:11.0.2&
sleep 60
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh config credentials –server http://localhost:8888/auth –realm master –user spring –password spring123 && \
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh create -x “client-scopes” -r master -s name=TEST -s protocol=openid-connect && \
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh create clients -r master -s clientId=spring-without-test-scope -s enabled=true -s clientAuthenticatorType=client-secret -s secret=79a93cb3-b460-40c8-8c96-c8c8bfe47d39 -s ‘redirectUris=[“*”]’ && \
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh create clients -r master -s clientId=spring-with-test-scope -s enabled=true -s clientAuthenticatorType=client-secret -s secret=b129f0c2-a46a-4bdb-a059-4eca03639767 -s ‘redirectUris=[“*”]’ -s ‘defaultClientScopes=[“TEST”, “web-origins”, “profile”, “roles”, “email”]’

