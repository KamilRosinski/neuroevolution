import {Matrix} from './matrix';
import {ActivationFunction} from '../enums/activation-function';

export interface NeuralNetworkLayer {
  readonly weights: Matrix;
  readonly biases: Matrix;
  readonly activationFunction: ActivationFunction;
}
