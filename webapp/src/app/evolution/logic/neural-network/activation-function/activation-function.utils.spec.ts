import * as ActivationFunctionUtils from './activation-function.utils';
import {ActivationFunction} from '../../../model/settings';
import {VectorElementModifier} from '../vector/vector';

describe('ActivationFunctionUtils', () => {
  describe('sigmoid', () => {
    const sigmoidModifier: VectorElementModifier = ActivationFunctionUtils.getActivationFunctionModifier(ActivationFunction.SIGMOID);

    it('should return 0.5 for 0 input', () => {
      // given
      const input = 0;

      // when
      const result: number = sigmoidModifier(input);

      // then
      expect(result).toEqual(0.5);
    });
  });

  describe('ReLU', () => {
    const reLuModifier: VectorElementModifier = ActivationFunctionUtils.getActivationFunctionModifier(ActivationFunction.RE_LU);

    it('should return 0 for negative input', () => {
      // given
      const input = -7;

      // when
      const result: number = reLuModifier(input);

      // then
      expect(result).toEqual(0);
    });

    it('should return 0 for 0 input', () => {
      // given
      const input = 0;

      // when
      const result: number = reLuModifier(input);

      // then
      expect(result).toEqual(0);
    });

    it('should return input value for positive input', () => {
      // given
      const input = 3;

      // when
      const result: number = reLuModifier(input);

      // then
      expect(result).toEqual(input);
    });
  });
});
