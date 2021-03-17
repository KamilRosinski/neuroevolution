import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {EvolutionService} from '../services/evolution.service';
import {catchError, exhaustMap, map, withLatestFrom} from 'rxjs/operators';
import {concat, merge, Observable, of} from 'rxjs';
import {EvolutionStarted} from '../model/evolution-started';
import {NewGeneration} from '../model/new-generation';
import {Action, Store} from '@ngrx/store';
import {EvolutionState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';
import * as AppActions from '../../state/app.actions';
import * as EvolutionSelectors from './evolution.selectors';

@Injectable()
export class EvolutionEffects {

  readonly scheduleEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.scheduleEvolution),
    withLatestFrom(this.store.select(EvolutionSelectors.selectSettings)),
    exhaustMap(([action, settings]) => concat(
      this.evolutionService.sendStartEvolution(settings),
      of(EvolutionActions.evolutionScheduled()),
      this.mapEvolutionMessages(),
      of(EvolutionActions.evolutionStopped())
    ).pipe(
      catchError(error => of(
        EvolutionActions.errorOccurred(),
        AppActions.createErrorMessage({message: error})
      ))
    ))
  ));

  readonly stopEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.stopEvolution),
    withLatestFrom(this.store.select(EvolutionSelectors.selectEvolutionId)),
    exhaustMap(([action, evolutionId]) => concat(
      this.evolutionService.sendStopEvolution(evolutionId),
      of(EvolutionActions.stoppingEvolution())
    ).pipe(
      catchError(error => of(
        EvolutionActions.errorOccurred(),
        AppActions.createErrorMessage({message: error})
      ))
    ))
  ));

  constructor(private readonly actions$: Actions,
              private readonly evolutionService: EvolutionService,
              private readonly store: Store<EvolutionState>) {
  }

  private mapEvolutionMessages(): Observable<Action> {
    return merge(this.mapEvolutionStartedMessage(), this.mapGenerationEvaluatedMessage());
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
