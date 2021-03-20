import {ErrorMessage, ErrorMessageState} from './error-message.state';
import {createReducer, on} from '@ngrx/store';
import {createEntityAdapter} from '@ngrx/entity';
import * as ErrorMessageActions from './error-message.actions';

export const errorMessageEntityAdapter = createEntityAdapter<ErrorMessage>();

export const errorMessageReducer = createReducer(
  errorMessageEntityAdapter.getInitialState(),
  on(ErrorMessageActions.errorMessageCreated, (state: ErrorMessageState, props: { errorMessage: ErrorMessage }) =>
    errorMessageEntityAdapter.addOne(props.errorMessage, state)
  ),
  on(ErrorMessageActions.deleteErrorMessage, (state: ErrorMessageState, props: { id: number }) =>
    errorMessageEntityAdapter.removeOne(props.id, state)
  )
);
