import {errorMessageEntityAdapter} from './app.reducer';
import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AppState} from './app.state';

const {
  selectAll
} = errorMessageEntityAdapter.getSelectors();

const selectApp = createFeatureSelector<AppState>('errorMessages');

export const selectAllErrorMessages = createSelector(selectApp, selectAll);
