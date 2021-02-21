import {createAction, props} from '@ngrx/store';

export const scheduleEvolution = createAction('[evolution] Schedule evolution', props<{range: number}>());
export const evolutionScheduled = createAction('[evolution] Evolution scheduled');
export const evolutionStarted = createAction('[evolution] Evolution started', props<{evolutionId: string}>());
export const generationEvaluated = createAction('[evolution] Generation evaluated', props<{id: number, score: number}>());
export const stopEvolution = createAction('[evolution] Stop evolution', props<{evolutionId: string}>());
export const stoppingEvolution = createAction('[evolution] Stopping evolution');
export const evolutionStopped = createAction('[evolution] Evolution stopped');
export const errorOccurred = createAction('[evolution] Error occurred', props<{message: string}>());
export const resetEvolution = createAction('[evolution] Reset evolution');
