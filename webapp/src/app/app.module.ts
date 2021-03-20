import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {StoreModule} from '@ngrx/store';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import {EffectsModule} from '@ngrx/effects';
import {ErrorMessagesComponent} from './components/error-messages/error-messages.component';
import {ErrorMessageComponent} from './components/error-message/error-message.component';
import {errorMessageReducer} from './state/error-message.reducer';
import {ErrorMessageEffects} from './state/error-message.effects';

@NgModule({
  declarations: [
    AppComponent,
    ErrorMessagesComponent,
    ErrorMessageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    StoreModule.forRoot({errorMessages: errorMessageReducer}, {
      runtimeChecks: {
        strictActionImmutability: true,
        strictActionSerializability: true,
        strictStateImmutability: true,
        strictStateSerializability: true
      }
    }),
    EffectsModule.forRoot([ErrorMessageEffects]),
    StoreDevtoolsModule.instrument({
      name: 'Evolution',
      maxAge: 25,
      logOnly: environment.production
    })
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
