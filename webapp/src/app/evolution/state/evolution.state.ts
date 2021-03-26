export interface EvolutionState {
  settings?: EvolutionSettings;
  status: EvolutionStatus;
  generations: Generation[];
}

export enum EvolutionStatus {
  NEW = 'NEW',
  RUNNING = 'RUNNING',
  STOPPED = 'STOPPED',
  ERROR = 'ERROR'
}

export interface EvolutionSettings {
  range: number;
}

export interface Generation {
  id: number;
  score: number;
}

export const initialState: EvolutionState = {
  status: EvolutionStatus.NEW,
  generations: [],
};
