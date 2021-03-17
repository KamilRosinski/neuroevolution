import {createAction, props} from '@ngrx/store';
import {ErrorMessage} from './app.state';

export const createErrorMessage = createAction('[app] Create error message', props<{message: string}>());
export const errorMessageCreated = createAction('[app] Error message created', props<{errorMessage: ErrorMessage}>());
export const deleteErrorMessage = createAction('[app] Delete error message', props<{id: number}>());
