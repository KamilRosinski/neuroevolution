import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {map, withLatestFrom} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import {ErrorMessageState} from './error-message.state';
import * as ErrorMessageActions from './error-message.actions';
import * as ErrorMessageSelectors from './error-message.selectors';

@Injectable()
export class ErrorMessageEffects {

  readonly createErrorMessage$ = createEffect(() => this.actions$.pipe(
    ofType(ErrorMessageActions.createErrorMessage),
    withLatestFrom(this.store.select(ErrorMessageSelectors.selectErrorMessageIds)),
    map(([action, ids]) => ErrorMessageActions.errorMessageCreated({
      errorMessage: {
        id: ids.length === 0 ? 0 : Math.max(...ids as number[]) + 1,
        timestamp: Date.now(),
        message: action.message
      }
    }))
  ));

  constructor(private readonly actions$: Actions,
              private readonly store: Store<ErrorMessageState>) {
  }

}
