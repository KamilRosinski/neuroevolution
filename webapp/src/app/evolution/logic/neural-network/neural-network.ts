import {NeuralNetworkLayer} from './neural-network-layer';

export interface NeuralNetwork {
  readonly layers: readonly NeuralNetworkLayer[];
}
