import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {EvolutionService} from '../services/evolution.service';
import {catchError, endWith, exhaustMap, map, startWith, tap, withLatestFrom} from 'rxjs/operators';
import {of} from 'rxjs';
import {Store} from '@ngrx/store';
import {EvolutionState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';
import * as ErrorMessageActions from '../../state/error-message.actions';
import * as EvolutionSelectors from './evolution.selectors';
import {Generation} from '../model/generation';

@Injectable()
export class EvolutionEffects {

  readonly startEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.startEvolution),
    withLatestFrom(this.store.select(EvolutionSelectors.selectSettings)),
    exhaustMap(([_, settings]) =>
      this.evolutionService.startEvolution(settings).pipe(
        map((generation: Generation) => EvolutionActions.generationReceived({generation})),
        startWith(EvolutionActions.evolutionStarted()),
        catchError((error: ErrorEvent) => of(
          ErrorMessageActions.createErrorMessage({message: error.message})
        )),
        endWith(EvolutionActions.evolutionFinished())
      )
    )
  ));

  readonly stopEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.stopEvolution),
    tap(() => this.evolutionService.stopEvolution())
  ), {
    dispatch: false
  });

  constructor(private readonly actions$: Actions,
              private readonly evolutionService: EvolutionService,
              private readonly store: Store<EvolutionState>) {
  }

}
