import {createAction, props} from '@ngrx/store';
import {EvolutionSettings} from './evolution.state';

export const createEvolution = createAction('[evolution] Create evolution', props<{settings: EvolutionSettings}>());
export const scheduleEvolution = createAction('[evolution] Schedule evolution');
export const evolutionScheduled = createAction('[evolution] Evolution scheduled');
export const evolutionStarted = createAction('[evolution] Evolution started', props<{evolutionId: string}>());
export const generationEvaluated = createAction('[evolution] Generation evaluated', props<{id: number, score: number}>());
export const stopEvolution = createAction('[evolution] Stop evolution');
export const stoppingEvolution = createAction('[evolution] Stopping evolution');
export const evolutionStopped = createAction('[evolution] Evolution stopped');
export const errorOccurred = createAction('[evolution] Error occurred', props<{message: string}>());
export const resetEvolution = createAction('[evolution] Reset evolution');
