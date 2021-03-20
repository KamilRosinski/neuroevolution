import {createReducer, on} from '@ngrx/store';
import {EvolutionSettings, EvolutionState, EvolutionStatus, initialState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';

export const evolutionReducer = createReducer(
  initialState,
  on(EvolutionActions.createEvolution, (state: EvolutionState, props: { settings: EvolutionSettings }) => ({
    ...state,
    settings: props.settings
  })),
  on(EvolutionActions.evolutionScheduled, (state: EvolutionState) => ({
    ...state,
    status: EvolutionStatus.SCHEDULED
  })),
  on(EvolutionActions.evolutionStarted, (state: EvolutionState, props: { evolutionId: string }) => ({
    ...state,
    id: props.evolutionId,
    status: EvolutionStatus.RUNNING
  })),
  on(EvolutionActions.generationEvaluated, (state: EvolutionState, props: { id: number, score: number }) => ({
    ...state,
    generations: [...state.generations, props]
  })),
  on(EvolutionActions.stoppingEvolution, (state: EvolutionState) => ({
    ...state,
    status: EvolutionStatus.STOPPING
  })),
  on(EvolutionActions.evolutionStopped, (state: EvolutionState) => ({
    ...state,
    status: EvolutionStatus.STOPPED,
    id: undefined
  })),
  on(EvolutionActions.errorOccurred, (state: EvolutionState) => ({
    ...state,
    status: EvolutionStatus.ERROR,
  })),
  on(EvolutionActions.resetEvolution, () => initialState)
);
