import {Matrix} from './matrix';

export type ActivationFunction = (value: number) => number;

export interface NeuralNetworkLayer {
  readonly weights: Matrix;
  readonly biases: Matrix;
  readonly activationFunction: ActivationFunction;
}
