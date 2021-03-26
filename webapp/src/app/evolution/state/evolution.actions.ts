import {createAction, props} from '@ngrx/store';
import {Generation} from '../model/generation';
import {Settings} from '../model/settings';

export const createEvolution = createAction('[evolution] Create evolution', props<{settings: Settings}>());
export const startEvolution = createAction('[evolution] Start evolution');
export const evolutionStarted = createAction('[evolution] Evolution started');
export const generationReceived = createAction('[evolution] Generation received', props<{generation: Generation}>());
export const stopEvolution = createAction('[evolution] Stop evolution');
export const evolutionFinished = createAction('[evolution] Evolution finished');
export const resetEvolution = createAction('[evolution] Reset evolution');
