import {NeuralNetwork} from './neural-network';
import {Matrix} from './matrix/matrix';
import {NeuralNetworkLayer} from './neural-network-layer';
import * as NeuralNetworkLayerUtils from './neural-network-layer.utils';

export const evaluate = (network: NeuralNetwork, input: Matrix): Matrix =>
  network.layers.reduce((accumulator: Matrix, layer: NeuralNetworkLayer) => NeuralNetworkLayerUtils.evaluate(layer, accumulator), input);
