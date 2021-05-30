import {ActivationFunction} from './settings';
import {ElementModifier} from '../utils/matrix.utils';

const elementModifiers: Map<ActivationFunction, ElementModifier> = new Map<ActivationFunction, ElementModifier>([
  [ActivationFunction.RE_LU, (value: number): number => Math.max(0, value)]
]);

export const findElementModifier = (activationFunction: ActivationFunction): ElementModifier => elementModifiers.get(activationFunction);
