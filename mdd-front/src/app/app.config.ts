import { ApplicationConfig } from '@angular/core';
import { provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { bootstrapApplication } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http'; // Importer HTTP_INTERCEPTORS
// import { XsrfInterceptor } from './interceptors/Xsrf.interceptor';

import { routes } from './app.routes';
import { AppComponent } from './app.component';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    ReactiveFormsModule,
  ],
};

bootstrapApplication(AppComponent, {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    BrowserAnimationsModule, // Module pour les animations Angular Material
    MatButtonModule, // Modules Angular Material
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
});
