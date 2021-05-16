import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {EvolutionService} from '../services/evolution.service';
import {catchError, exhaustMap, finalize, first, map, startWith, tap, withLatestFrom} from 'rxjs/operators';
import {concat, of} from 'rxjs';
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
    exhaustMap(([_, settings]) => concat(
      this.evolutionService.startEvolution(settings),
      of(EvolutionActions.evolutionStarted()),
      this.evolutionService.generations$.pipe(
        map((generation: Generation) => EvolutionActions.generationReceived({generation})),
        catchError(error => of(
          ErrorMessageActions.createErrorMessage({message: error.message})
        ))
      )
    ))
  ));

  readonly stopEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.stopEvolution),
    exhaustMap(() => concat(
      this.evolutionService.stopEvolution(),
      of(EvolutionActions.evolutionFinished())
    ))
  ));

  constructor(private readonly actions$: Actions,
              private readonly evolutionService: EvolutionService,
              private readonly store: Store<EvolutionState>) {
  }

}
