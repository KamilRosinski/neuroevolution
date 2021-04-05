import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OverviewComponent} from './components/overview/overview.component';
import {SettingsComponent} from './components/settings/settings.component';
import {OverviewActivateGuard} from './guards/overview-activate.guard';
import {OverviewDeactivateGuard} from './guards/overview-deactivate.guard';

const routes: Routes = [
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'overview',
    component: OverviewComponent,
    canActivate: [OverviewActivateGuard],
    canDeactivate: [OverviewDeactivateGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    OverviewActivateGuard,
    OverviewDeactivateGuard
  ]
})
export class EvolutionRoutingModule {
}
