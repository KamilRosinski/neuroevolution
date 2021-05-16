export interface Settings {
  game: GameSettings;
  evolution: EvolutionSettings;
  neuralNetwork: NeuralNetworkSettings;
}

export interface GameSettings {
  fieldWidth: number;
  fieldHeight: number;
  snakeEndurance: number;
}

export interface EvolutionSettings {
  generationSize: number;
  mutationProbability: number;
  mutationRate: number;
}

export interface NeuralNetworkSettings {
  hiddenLayers: LayerSettings[];
}

export interface LayerSettings {
  numberOfNeurons: number;
  activationFunction: ActivationFunction;
}

export enum ActivationFunction {
  RE_LU = 'ReLU',
  SIGMOID = 'sigmoid'
}
