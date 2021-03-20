import {errorMessageEntityAdapter} from './error-message.reducer';
import {createFeatureSelector, createSelector} from '@ngrx/store';
import {ErrorMessageState} from './error-message.state';

const {
  selectAll,
  selectIds
} = errorMessageEntityAdapter.getSelectors();

const selectErrorMessages = createFeatureSelector<ErrorMessageState>('errorMessages');

export const selectAllErrorMessages = createSelector(selectErrorMessages, selectAll);

export const selectErrorMessageIds = createSelector(selectErrorMessages, selectIds);
