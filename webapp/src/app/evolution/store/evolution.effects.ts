import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {EvolutionService} from '../services/evolution.service';
import * as EvolutionActions from './evolution.actions';
import {catchError, map, mergeMap, tap} from 'rxjs/operators';
import {concat, of} from 'rxjs';
import {Message} from '../model/message';
import {ActionType} from '../model/action-type';
import {EvolutionStarted} from '../model/evolution-started';
import {NewGeneration} from '../model/new-generation';

@Injectable()
export class EvolutionEffects {

  readonly scheduleEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.scheduleEvolution),
    tap((action) => this.evolutionService.sendStartEvolution({
      range: action.range
    })),
    mergeMap(() => concat(
      of(EvolutionActions.evolutionScheduled()),
      this.evolutionService.receiveMessage().pipe(
        map((message: Message) => {
          switch (message.action) {
            case ActionType.EVOLUTION_STARTED:
              return EvolutionActions.evolutionStarted({
                evolutionId: (message.body as EvolutionStarted).evolutionId
              });
            case ActionType.NEW_GENERATION:
              const body: NewGeneration = message.body as NewGeneration;
              return EvolutionActions.generationEvaluated({
                id: body.id,
                score: body.value
              });
            default:
              throw Error('Unrecognized message');
          }
        }),
        catchError(() => of(EvolutionActions.errorOccurred({message: 'aaa'})))
      ),
      of(EvolutionActions.evolutionStopped())
    ))
  ));

  readonly stopEvolution$ = createEffect(() => this.actions$.pipe(
    ofType(EvolutionActions.stopEvolution),
    tap((action) => this.evolutionService.sendStopEvolution({
      evolutionId: action.evolutionId
    })),
    map(() => EvolutionActions.stoppingEvolution())
  ));

  constructor(private readonly actions$: Actions,
              private readonly evolutionService: EvolutionService) {
  }


}
