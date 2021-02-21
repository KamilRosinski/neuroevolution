import {Action, createReducer, on} from '@ngrx/store';
import {EvolutionState, EvolutionStatus, initialState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';

export function evolutionReducer(s: EvolutionState, action: Action): EvolutionState {
  return createReducer(
    initialState,
    on(EvolutionActions.evolutionScheduled, (state: EvolutionState) => ({
      ...state,
      status: EvolutionStatus.SCHEDULED
    })),
    on(EvolutionActions.evolutionStarted, (state: EvolutionState, props: {evolutionId: string}) => ({
      ...state,
      id: props.evolutionId,
      status: EvolutionStatus.RUNNING
    })),
    on(EvolutionActions.generationEvaluated, (state: EvolutionState, props: {id: number, score: number}) => ({
      ...state,
      generations: [...state.generations, props]
    })),
    on(EvolutionActions.stoppingEvolution, (state: EvolutionState) => ({
      ...state,
      status: EvolutionStatus.STOPPING
    })),
    on(EvolutionActions.evolutionStopped, (state: EvolutionState) => ({
      ...state,
      status: EvolutionStatus.STOPPED
    })),
    on(EvolutionActions.errorOccurred, (state: EvolutionState, props: {message: string}) => ({
      ...state,
      status: EvolutionStatus.ERROR,
      errorMessage: props.message
    })),
    on(EvolutionActions.resetEvolution, () => initialState)
  )(s, action);
}
