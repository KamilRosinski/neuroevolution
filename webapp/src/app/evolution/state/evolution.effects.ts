import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {EvolutionService} from '../services/evolution.service';
import {catchError, filter, map, mergeMap, tap, withLatestFrom} from 'rxjs/operators';
import {concat, merge, Observable, of} from 'rxjs';
import {EvolutionStarted} from '../model/evolution-started';
import {NewGeneration} from '../model/new-generation';
import {Action, Store} from '@ngrx/store';
import {EvolutionSettings, EvolutionState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';
import * as EvolutionSelectors from './evolution.selectors';

@Injectable()
export class EvolutionEffects {

  readonly scheduleEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.scheduleEvolution),
    withLatestFrom(this.store.select(EvolutionSelectors.selectSettings)),
    tap(([action, settings]: [Action, EvolutionSettings | undefined]) => {
      if (settings) {
        this.evolutionService.sendStartEvolution(settings);
      }
    }),
    mergeMap(() => concat(
      of(EvolutionActions.evolutionScheduled()),
      this.mapEvolutionMessages(),
      of(EvolutionActions.evolutionStopped())
    ))
  ));

  readonly stopEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.stopEvolution),
    withLatestFrom(this.store.select(EvolutionSelectors.selectEvolutionId)),
    tap(([action, evolutionId]: [Action, string | undefined]) => {
      if (evolutionId) {
        this.evolutionService.sendStopEvolution({evolutionId});
      }
    }),
    map(() => EvolutionActions.stoppingEvolution())
  ));

  constructor(private readonly actions$: Actions,
              private readonly evolutionService: EvolutionService,
              private readonly store: Store<EvolutionState>) {
  }

  private mapEvolutionMessages(): Observable<Action> {
    return merge(this.mapEvolutionStartedMessage(), this.mapGenerationEvaluatedMessage()).pipe(
      catchError(error => of(EvolutionActions.errorOccurred({message: JSON.stringify(error)})))
    );
  }

  private mapEvolutionStartedMessage(): Observable<Action> {
    return this.evolutionService.receiveEvolutionStarted().pipe(
      map((message: EvolutionStarted) => EvolutionActions.evolutionStarted({evolutionId: message.evolutionId}))
    );
  }

  private mapGenerationEvaluatedMessage(): Observable<Action> {
    return this.evolutionService.receiveNewGeneration().pipe(
      map((message: NewGeneration) => EvolutionActions.generationEvaluated({id: message.id, score: message.value}))
    );
  }

}
