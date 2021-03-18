import {errorMessageEntityAdapter} from './app.reducer';
import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AppState, ErrorMessage} from './app.state';

const {
  selectAll
} = errorMessageEntityAdapter.getSelectors();

const selectApp = createFeatureSelector<AppState>('errorMessages');

export const selectAllErrorMessages = createSelector(selectApp, selectAll);

export const selectMaxId = createSelector(selectAllErrorMessages, messages => Math.max(0, ...messages.map(message => message.id)));
