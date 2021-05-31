import {Matrix} from '../model/matrix';
import {NeuralNetworkLayer} from '../model/neural-network-layer';
import * as MatrixUtils from './matrix.utils';
import * as ActivationFunctionUtils from './activation-function.utils';

export const evaluate = (layer: NeuralNetworkLayer, input: Matrix): Matrix => MatrixUtils.apply(
  MatrixUtils.add(MatrixUtils.multiply(layer.weights, input), layer.biases),
  ActivationFunctionUtils.getElementModifier(layer.activationFunction)
);
