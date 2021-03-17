export interface EvolutionState {
  id?: string;
  settings?: EvolutionSettings;
  status: EvolutionStatus;
  generations: {id: number, score: number}[];
}

export enum EvolutionStatus {
  NEW = 'NEW',
  SCHEDULED = 'SCHEDULED',
  RUNNING = 'RUNNING',
  STOPPING = 'STOPPING',
  STOPPED = 'STOPPED',
  ERROR = 'ERROR'
}

export interface EvolutionSettings {
  range: number;
}

export const initialState: EvolutionState = {
  status: EvolutionStatus.NEW,
  generations: [],
};
