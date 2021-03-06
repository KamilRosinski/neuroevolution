import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {EvolutionSettings, EvolutionState} from '../store/evolution.state';
import {Observable} from 'rxjs';
import {Store} from '@ngrx/store';
import * as EvolutionSelectors from '../store/evolution.selectors';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EvolutionOverviewGuard implements CanActivate {

  constructor(private readonly store: Store<EvolutionState>,
              private readonly router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> {
    return this.store.select(EvolutionSelectors.selectSettings).pipe(
      map((settings: EvolutionSettings | undefined) => settings ? true : this.router.createUrlTree(['evolution', 'new']))
    );
  }

}
