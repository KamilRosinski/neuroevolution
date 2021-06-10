import {Matrix} from './matrix/matrix';
import {ActivationFunction} from './activation-function/activation-function';
import {Vector, VectorElementModifier} from './vector/vector';
import * as ActivationFunctionUtils from './activation-function/activation-function.utils';

export class NeuralNetworkLayer {

  readonly activationFunctionModifier: VectorElementModifier;

  constructor(public readonly weights: Matrix,
              public readonly biases: Vector,
              activationFunction: ActivationFunction) {
    this.activationFunctionModifier = ActivationFunctionUtils.getActivationFunctionModifier(activationFunction);
  }

  evaluate(input: Vector): Vector {
    return this.weights.multiply(input).add(this.biases).apply(this.activationFunctionModifier);
  }

}
