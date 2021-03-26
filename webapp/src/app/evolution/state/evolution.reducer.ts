import {createReducer, on} from '@ngrx/store';
import {EvolutionState, EvolutionStatus, initialState} from './evolution.state';
import * as EvolutionActions from './evolution.actions';
import {Generation} from '../model/generation';
import {Settings} from '../model/settings';

export const evolutionReducer = createReducer(
  initialState,
  on(EvolutionActions.createEvolution, (state: EvolutionState, props: { settings: Settings }): EvolutionState => ({
    ...state,
    settings: props.settings
  })),
  on(EvolutionActions.evolutionStarted, (state: EvolutionState): EvolutionState => ({
    ...state,
    status: EvolutionStatus.RUNNING
  })),
  on(EvolutionActions.generationReceived, (state: EvolutionState, props: { generation: Generation }): EvolutionState => ({
    ...state,
    generations: [...state.generations, props.generation]
  })),
  on(EvolutionActions.evolutionFinished, (state: EvolutionState): EvolutionState => ({
    ...state,
    status: EvolutionStatus.FINISHED,
  })),
  on(EvolutionActions.resetEvolution, () => initialState)
);
