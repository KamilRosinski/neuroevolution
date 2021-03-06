import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EvolutionOverviewComponent} from './components/evolution-overview/evolution-overview.component';
import {CreateEvolutionComponent} from './components/create-evolution/create-evolution.component';
import {EvolutionOverviewGuard} from './guards/evolution-overview.guard';

const routes: Routes = [
  {
    path: 'new',
    component: CreateEvolutionComponent
  },
  {
    path: 'overview',
    component: EvolutionOverviewComponent,
    canActivate: [EvolutionOverviewGuard]
  }
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
