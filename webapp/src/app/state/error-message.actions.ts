import {createAction, props} from '@ngrx/store';
import {ErrorMessage} from './error-message.state';

export const createErrorMessage = createAction('[error messages] Create error message', props<{message: string}>());
export const errorMessageCreated = createAction('[error messages] Error message created', props<{errorMessage: ErrorMessage}>());
export const deleteErrorMessage = createAction('[error messages] Delete error message', props<{id: number}>());
