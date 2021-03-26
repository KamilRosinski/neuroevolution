import {createFeatureSelector, createSelector} from '@ngrx/store';
import {EvolutionState} from './evolution.state';

const selectFeature = createFeatureSelector<EvolutionState>('evolution');

export const selectSettings = createSelector(selectFeature, (state: EvolutionState) => state.settings);
export const selectEvolutionStatus = createSelector(selectFeature, (state: EvolutionState) => state.status);
export const selectGenerations = createSelector(selectFeature, (state: EvolutionState) => state.generations);
