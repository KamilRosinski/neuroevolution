import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EvolutionRoutingModule} from './evolution-routing.module';
import {EvolutionOverviewComponent} from './components/evolution-overview/evolution-overview.component';
import {StoreModule} from '@ngrx/store';
import {evolutionReducer} from './store/evolution.reducer';
import {EffectsModule} from '@ngrx/effects';
import {EvolutionEffects} from './store/evolution.effects';
import {CreateEvolutionComponent} from './components/create-evolution/create-evolution.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    CreateEvolutionComponent,
    EvolutionOverviewComponent
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
