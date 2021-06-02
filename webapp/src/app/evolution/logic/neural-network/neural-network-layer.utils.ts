import {Matrix} from './matrix/matrix';
import {NeuralNetworkLayer} from './neural-network-layer';
import * as MatrixUtils from './matrix/matrix.utils';
import * as ActivationFunctionUtils from './activation-function/activation-function.utils';

export const evaluate = (layer: NeuralNetworkLayer, input: Matrix): Matrix =>
  MatrixUtils.apply(
    MatrixUtils.add(
      MatrixUtils.multiply(
        layer.weights,
        input),
      layer.biases),
    ActivationFunctionUtils.getElementModifier(layer.activationFunction)
  );
