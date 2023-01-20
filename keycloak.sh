#!/bin/bash

docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user spring --password spring123 &&
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh create -x "client-scopes" -r master -s name=QUIZ_PLAY -s protocol=openid-connect &&
docker exec -it keycloak /opt/jboss/keycloak/bin/kcadm.sh create clients -r master -s clientId=quiz-gateway -s enabled=true -s clientAuthenticatorType=client-secret -s secret=c6480137-1526-4c3e-aed3-295aabcb7609  -s 'redirectUris=["*"]' -s 'defaultClientScopes=["QUIZ_PLAY", "web-origins", "profile", "roles", "email"]'
