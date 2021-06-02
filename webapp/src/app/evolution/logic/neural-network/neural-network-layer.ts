import {Matrix} from './matrix/matrix';
import {ActivationFunction} from './activation-function/activation-function';

export interface NeuralNetworkLayer {
  readonly weights: Matrix;
  readonly biases: Matrix;
  readonly activationFunction: ActivationFunction;
}
