import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './components/overview/overview.component';
import {SettingsComponent} from './components/settings/settings.component';
import {EvolutionOverviewGuard} from './guards/evolution-overview.guard';

const routes: Routes = [
  {
    path: 'new',
    component: SettingsComponent
  },
  {
    path: 'overview',
    component: OverviewComponent,
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
