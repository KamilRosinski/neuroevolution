import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import * as AppActions from './app.actions';
import {map, withLatestFrom} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import {AppState} from './app.state';
import * as AppSelectors from './app.selectors'

@Injectable()
export class AppEffects {

  readonly createErrorMessage$ = createEffect(() => this.actions$.pipe(
    ofType(AppActions.createErrorMessage),
    withLatestFrom(this.store.select(AppSelectors.selectMaxId)),
    map(([action, maxId]) => AppActions.errorMessageCreated({
      errorMessage: {
        id: maxId + 1,
        timestamp: Date.now(),
        message: action.message
      }
    }))
  ));

  constructor(private readonly actions$: Actions,
              private readonly store: Store<AppState>) {
  }

}
