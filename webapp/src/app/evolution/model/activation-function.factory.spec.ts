import * as ActivationFunctionFactory from './activation-function.factory';
import {ActivationFunction} from './settings';
import {ElementModifier} from '../utils/matrix.utils';

describe('ActivationFunctionFactory', () => {
  describe('ReLU', () => {
    const modifier: ElementModifier = ActivationFunctionFactory.findElementModifier(ActivationFunction.RE_LU);

    it('should return 0 for negative input', () => {
      // given
      const input = -7;

      // when
      const result: number = modifier(input);

      // then
      expect(result).toEqual(0);
    });

    it('should return 0 for 0 input', () => {
      // given
      const input = 0;

      // when
      const result: number = modifier(input);

      // then
      expect(result).toEqual(0);
    });

    it('should return input value for positive input', () => {
      // given
      const input = 3;

      // when
      const result: number = modifier(input);

      // then
      expect(result).toEqual(input);
    });
  });
});
