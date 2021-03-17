import {AppState, ErrorMessage} from './app.state';
import {Action, createReducer, on} from '@ngrx/store';
import {createEntityAdapter} from '@ngrx/entity';
import * as AppActions from './app.actions';

export const errorMessageEntityAdapter = createEntityAdapter<ErrorMessage>();

export function appReducer(s: any, action: Action): AppState {
  return createReducer(
    errorMessageEntityAdapter.getInitialState(),
    on(AppActions.errorMessageCreated, (state: AppState, props: {errorMessage: ErrorMessage}) => errorMessageEntityAdapter.addOne(props.errorMessage, state)),
    on(AppActions.deleteErrorMessage, (state: AppState, props: {id: number}) => errorMessageEntityAdapter.removeOne(props.id, state))
  )(s, action);
}
