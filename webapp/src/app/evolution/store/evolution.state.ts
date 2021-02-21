export interface EvolutionState {
  id: string;
  status: EvolutionStatus;
  generations: {id: number, score: number}[];
  errorMessage: string;
}

export enum EvolutionStatus {
  NEW = 'NEW',
  SCHEDULED = 'SCHEDULED',
  RUNNING = 'RUNNING',
  STOPPING = 'STOPPING',
  STOPPED = 'STOPPED',
  ERROR = 'ERROR'
}

export const initialState: EvolutionState = {
  id: '',
  status: EvolutionStatus.NEW,
  generations: [],
  errorMessage: ''
};
