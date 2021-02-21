import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EvolutionRoutingModule} from './evolution-routing.module';
import {EvolutionComponent} from './components/evolution/evolution.component';


@NgModule({
  declarations: [
    EvolutionComponent
  ],
  imports: [
    CommonModule,
    EvolutionRoutingModule
  ]
})
export class EvolutionModule {
}
