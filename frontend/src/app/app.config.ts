import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideAuth } from 'angular-auth-oidc-client';
import { authConfig } from './config/auth.config';
import { authInterceptor } from './interceptor/auth-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    //provideHttpClient(withFetch()), // recommended to enable `fetch` for applications that use Server-Side Rendering
    provideHttpClient(withInterceptors([authInterceptor])),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    //provideClientHydration(withEventReplay()),
    provideAuth(authConfig)
  ]
};
