import {ActionType} from './action-type';
import {StartGenerator} from './start-generator';
import {GeneratorStarted} from './generator-started';
import {ValueGenerated} from './value-generated';
import {StopGenerator} from './stop-generator';

export interface Message {
  action: ActionType;
  body: StartGenerator | GeneratorStarted | ValueGenerated | StopGenerator;
}
