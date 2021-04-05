import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Store} from '@ngrx/store';
import * as EvolutionSelectors from '../state/evolution.selectors';
import * as ErrorMessageActions from '../../state/error-message.actions';
import {map} from 'rxjs/operators';
import {EvolutionState, EvolutionStatus} from '../state/evolution.state';
import {OverviewComponent} from '../components/overview/overview.component';

@Injectable()
export class OverviewDeactivateGuard implements CanDeactivate<OverviewComponent> {

  constructor(private readonly store: Store<EvolutionState>) {
  }

  canDeactivate(component: OverviewComponent, currentRoute: ActivatedRouteSnapshot, currentState: RouterStateSnapshot,
                nextState?: RouterStateSnapshot): Observable<boolean> {
    return this.store.select(EvolutionSelectors.selectEvolutionStatus).pipe(
      map((status: EvolutionStatus) => {
        const canDeactivate: boolean = status !== EvolutionStatus.RUNNING;
        if (!canDeactivate) {
          this.store.dispatch(ErrorMessageActions.createErrorMessage({
            message: 'Cannot navigate. The evolution is still running.'
          }));
        }
        return canDeactivate;
      })
    );
  }

}
