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
import {appReducer} from './state/app.reducer';
import {AppEffects} from './state/app.effects';

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
    StoreModule.forRoot({errorMessages: appReducer}, {
      runtimeChecks: {
        strictActionImmutability: true,
        strictActionSerializability: true,
        strictStateImmutability: true,
        strictStateSerializability: true
      }
    }),
    EffectsModule.forRoot([AppEffects]),
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
