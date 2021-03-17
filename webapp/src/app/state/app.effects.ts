import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {SequenceGeneratorService} from '../services/sequence-generator.service';
import * as AppActions from './app.actions';
import {map} from 'rxjs/operators';

@Injectable()
export class AppEffects {

  readonly createErrorMessage$ = createEffect(() => this.actions$.pipe(
    ofType(AppActions.createErrorMessage),
    map(action => AppActions.errorMessageCreated({
      errorMessage: {
        id: this.sequenceGenerator.generate(),
        timestamp: Date.now(),
        message: action.message
      }
    }))
  ));

  constructor(private readonly actions$: Actions,
              private readonly sequenceGenerator: SequenceGeneratorService) {
  }

}
