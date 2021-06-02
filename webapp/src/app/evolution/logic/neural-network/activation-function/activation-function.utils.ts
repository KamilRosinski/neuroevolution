import {ActivationFunction} from '../../../model/settings';
import {MatrixElementModifier} from '../matrix/matrix.utils';

const sigmoid: MatrixElementModifier = (value: number): number => 1 / (1 + Math.exp(-value));
const reLu: MatrixElementModifier = (value: number): number => Math.max(0, value);

const elementModifiers: ReadonlyMap<ActivationFunction, MatrixElementModifier> = new Map<ActivationFunction, MatrixElementModifier>([
  [ActivationFunction.SIGMOID, sigmoid],
  [ActivationFunction.RE_LU, reLu]
]);

export const getElementModifier = (activationFunction: ActivationFunction): MatrixElementModifier => elementModifiers.get(activationFunction);
