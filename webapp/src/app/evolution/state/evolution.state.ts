import {Settings} from '../model/settings';
import {Generation} from '../model/generation';

export interface EvolutionState {
  settings?: Settings;
  status: EvolutionStatus;
  generations: Generation[];
}

export enum EvolutionStatus {
  NEW = 'NEW',
  RUNNING = 'RUNNING',
  FINISHED = 'FINISHED',
}

export const initialState: EvolutionState = {
  status: EvolutionStatus.NEW,
  generations: [],
};
