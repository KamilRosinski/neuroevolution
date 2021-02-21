import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EvolutionRoutingModule} from './evolution-routing.module';
import {EvolutionComponent} from './components/evolution/evolution.component';
import {StoreModule} from '@ngrx/store';
import {evolutionReducer} from './store/evolution.reducers';
import {EffectsModule} from '@ngrx/effects';
import {EvolutionEffects} from './store/evolution.effects';


@NgModule({
  declarations: [
    EvolutionComponent
  ],
  imports: [
    CommonModule,
    EvolutionRoutingModule,
    StoreModule.forFeature('evolution', evolutionReducer),
    EffectsModule.forFeature([EvolutionEffects])
  ]
})
export class EvolutionModule {
}
