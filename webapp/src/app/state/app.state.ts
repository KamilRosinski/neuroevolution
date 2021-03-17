import {EntityState} from '@ngrx/entity';

export interface AppState extends EntityState<ErrorMessage> {
}

export interface  ErrorMessage {
  id: number;
  timestamp: number;
  message: string;
}
