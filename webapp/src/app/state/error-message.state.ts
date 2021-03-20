import {EntityState} from '@ngrx/entity';

export interface ErrorMessageState extends EntityState<ErrorMessage> {
}

export interface ErrorMessage {
  id: number;
  timestamp: number;
  message: string;
}
