import {NeuralNetworkLayerSettings} from './neural-network-layer-settings';

export interface NeuralNetworkSettings {
  readonly inputSize: number;
  readonly layers: readonly NeuralNetworkLayerSettings[];
}
