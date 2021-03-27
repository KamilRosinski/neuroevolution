import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Store} from '@ngrx/store';
import * as EvolutionSelectors from '../state/evolution.selectors';
import {map} from 'rxjs/operators';
import {EvolutionState} from '../state/evolution.state';
import {Settings} from '../model/settings';

@Injectable({
  providedIn: 'root'
})
export class EvolutionOverviewGuard implements CanActivate {

  constructor(private readonly store: Store<EvolutionState>,
              private readonly router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> {
    return this.store.select(EvolutionSelectors.selectSettings).pipe(
      map((settings: Settings | undefined) => settings ? true : this.router.createUrlTree(['evolution', 'new']))
    );
  }

}
