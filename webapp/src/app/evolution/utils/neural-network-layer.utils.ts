import {Matrix} from '../model/matrix';
import {NeuralNetworkLayer} from '../model/neural-network-layer';
import * as MatrixUtils from './matrix.utils';

export const evaluate = (layer: NeuralNetworkLayer, input: Matrix): Matrix =>
  MatrixUtils.apply(MatrixUtils.add(MatrixUtils.multiply(layer.weights, input), layer.biases), layer.activationFunction);
