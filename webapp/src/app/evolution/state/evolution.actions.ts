import {createAction, props} from '@ngrx/store';
import {EvolutionSettings} from './evolution.state';

export const createEvolution = createAction('[evolution] Create evolution', props<{settings: EvolutionSettings}>());
export const startEvolution = createAction('[evolution] Start evolution');
export const evolutionStarted = createAction('[evolution] Evolution started');
export const generationReceived = createAction('[evolution] Generation received', props<{id: number, score: number}>());
export const stopEvolution = createAction('[evolution] Stop evolution');
export const evolutionFinished = createAction('[evolution] Evolution finished');
export const resetEvolution = createAction('[evolution] Reset evolution');
