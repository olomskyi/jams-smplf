import { LogLevel, PassedInitialConfig } from 'angular-auth-oidc-client';

export const authConfig: PassedInitialConfig = {
  config: {
    authority: 'http://localhost:8181/realms/jams-front-angular-realm',
    redirectUrl: typeof window !== 'undefined' ? window.location.origin : 'http://localhost:4200',
    postLogoutRedirectUri: typeof window !== 'undefined' ? window.location.origin : 'http://localhost:4200',
    clientId: 'jams-front-angular-client',
    scope: 'openid profile offline_access',
    responseType: 'code',
    silentRenew: true,
    useRefreshToken: true,
    renewTimeBeforeTokenExpiresInSeconds: 30,
    tokenRefreshInSeconds: 300,
    secureRoutes: ['http://localhost:9000/api'],
    startCheckSession: true,
    triggerAuthorizationResultEvent: true,
    ignoreNonceAfterRefresh: true,
    customParamsAuthRequest: {
      kc_idp_hint: 'keycloak'
    },
    logLevel: LogLevel.Debug
  }
};