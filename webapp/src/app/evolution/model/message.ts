import {ActionType} from './action-type';
import {NewGeneration} from './new-generation';
import {EvolutionStarted} from './evolution-started';
import {StartEvolution} from './start-evolution';
import {StopEvolution} from './stop-evolution';

export interface Message {
  action: ActionType;
  body: StartEvolution | EvolutionStarted | NewGeneration | StopEvolution;
}
