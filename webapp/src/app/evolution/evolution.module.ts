import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EvolutionRoutingModule} from './evolution-routing.module';
import {OverviewComponent} from './components/overview/overview.component';
import {StoreModule} from '@ngrx/store';
import {evolutionReducer} from './state/evolution.reducer';
import {EffectsModule} from '@ngrx/effects';
import {EvolutionEffects} from './state/evolution.effects';
import {SettingsComponent} from './components/settings/settings.component';
import {ReactiveFormsModule} from '@angular/forms';
import {ControlsComponent} from './components/controls/controls.component';


@NgModule({
  declarations: [
    SettingsComponent,
    OverviewComponent,
    ControlsComponent
  ],
  imports: [
    CommonModule,
    EvolutionRoutingModule,
    ReactiveFormsModule,
    StoreModule.forFeature('evolution', evolutionReducer),
    EffectsModule.forFeature([EvolutionEffects])
  ]
})
export class EvolutionModule {
}
