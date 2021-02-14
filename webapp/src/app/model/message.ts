import {ActionType} from './action-type';
import {NewGeneration} from './new-generation';
import {EvolutionStarted} from './evolution-started';

export interface Message {
  action: ActionType;
  body: EvolutionStarted | NewGeneration;
}
