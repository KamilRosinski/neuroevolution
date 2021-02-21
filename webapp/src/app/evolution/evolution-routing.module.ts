import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EvolutionComponent} from './components/evolution/evolution.component';

const routes: Routes = [
  {path: '', component: EvolutionComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class EvolutionRoutingModule {
}
