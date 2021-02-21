import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: 'evolution', pathMatch: 'full'},
  {path: 'evolution', loadChildren: () => import('./evolution/evolution.module').then(m => m.EvolutionModule)}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
