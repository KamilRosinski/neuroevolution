import {ActivationFunction} from '../../../model/settings';
import {VectorElementModifier} from '../vector/vector';

const sigmoid: VectorElementModifier = (value: number): number => 1 / (1 + Math.exp(-value));
const reLu: VectorElementModifier = (value: number): number => Math.max(0, value);

const modifiers: ReadonlyMap<ActivationFunction, VectorElementModifier> = new Map<ActivationFunction, VectorElementModifier>([
  [ActivationFunction.SIGMOID, sigmoid],
  [ActivationFunction.RE_LU, reLu]
]);

export const getActivationFunctionModifier = (activationFunction: ActivationFunction): VectorElementModifier =>
  modifiers.get(activationFunction);
