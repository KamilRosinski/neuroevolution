import {NeuralNetwork} from '../model/neural-network';
import {Matrix} from '../model/matrix';
import {NeuralNetworkLayer} from '../model/neural-network-layer';
import * as NeuralNetworkLayerUtils from './neural-network-layer.utils';

export const evaluate = (network: NeuralNetwork, input: Matrix): Matrix =>
  network.layers.reduce((accumulator: Matrix, layer: NeuralNetworkLayer) => NeuralNetworkLayerUtils.evaluate(layer, accumulator), input);
