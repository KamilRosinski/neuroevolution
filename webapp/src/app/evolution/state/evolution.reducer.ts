import {createReducer, on} from '@ngrx/store';
import {EvolutionSettings, EvolutionState, EvolutionStatus, initialState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';

export const evolutionReducer = createReducer(
  initialState,
  on(EvolutionActions.createEvolution, (state: EvolutionState, props: { settings: EvolutionSettings }): EvolutionState => ({
    ...state,
    settings: props.settings
  })),
  on(EvolutionActions.evolutionStarted, (state: EvolutionState): EvolutionState => ({
    ...state,
    status: EvolutionStatus.RUNNING
  })),
  on(EvolutionActions.generationReceived, (state: EvolutionState, props: { id: number, score: number }): EvolutionState => ({
    ...state,
    generations: [...state.generations, props]
  })),
  on(EvolutionActions.evolutionFinished, (state: EvolutionState): EvolutionState => ({
    ...state,
    status: EvolutionStatus.STOPPED,
  })),
  on(EvolutionActions.resetEvolution, () => initialState)
);
