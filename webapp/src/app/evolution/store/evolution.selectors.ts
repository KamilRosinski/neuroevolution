import {createSelector} from '@ngrx/store';
import {EvolutionState} from './evolution.state';

export const selectFeature = (state: any) => state.evolution;

export const selectEvolutionStatus = createSelector(selectFeature, (state: EvolutionState) => state.status);
export const selectEvolutionId = createSelector(selectFeature, (state: EvolutionState) => state.id);
export const selectGenerations = createSelector(selectFeature, (state: EvolutionState) => state.generations);
