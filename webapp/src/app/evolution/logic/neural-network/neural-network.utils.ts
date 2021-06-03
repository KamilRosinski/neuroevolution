import {NeuralNetwork} from './neural-network';
import {Matrix} from './matrix/matrix';
import {NeuralNetworkLayer} from './neural-network-layer';
import * as NeuralNetworkLayerUtils from './neural-network-layer.utils';
import {NeuralNetworkSettings} from './settings/neural-network-settings';
import * as MatrixUtils from './matrix/matrix.utils';
import {NeuralNetworkLayerSettings} from './settings/neural-network-layer-settings';
import {networkInterfaces} from 'os';
import {compareNumbers} from '@angular/compiler-cli/src/diagnostics/typescript_version';

export const create = (settings: NeuralNetworkSettings, values: IterableIterator<number>): NeuralNetwork => {
  const layers: NeuralNetworkLayer[] = [];
  for (let i = 0; i < settings.layers.length; ++i) {
    const inputSize: number = i === 0 ? settings.inputSize : settings.layers[i - 1].numberOfNeurons;
    const currentLayerSettings: NeuralNetworkLayerSettings = settings.layers[i];
    layers.push({
      weights: MatrixUtils.createFromProvider(currentLayerSettings.numberOfNeurons, inputSize, () => values.next().value),
      biases: MatrixUtils.createFromProvider(currentLayerSettings.numberOfNeurons, 1, () => values.next().value),
      activationFunction: currentLayerSettings.activationFunction
    });
  }
  return {
    layers
  };
};

export const evaluate = (network: NeuralNetwork, input: Matrix): Matrix =>
  network.layers.reduce((accumulator: Matrix, layer: NeuralNetworkLayer) => NeuralNetworkLayerUtils.evaluate(layer, accumulator), input);
