import {NeuralNetworkLayer} from '../model/neural-network-layer';
import {Matrix} from '../model/matrix';
import * as NeuralNetworkLayerUtils from './neural-network-layer.utils';
import {ActivationFunction} from '../enums/activation-function';

describe('NeuralNetworkLayerUtils', () => {
  describe('evaluate', () => {
    it('should evaluate neural network layer', () => {
      // given
      const layer: NeuralNetworkLayer = {
        weights: {
          height: 2,
          width: 3,
          elements: [[1, 0, 2], [-2, 1, -3]]
        },
        biases: {
          height: 2,
          width: 1,
          elements: [[2], [1]]
        },
        activationFunction: ActivationFunction.RE_LU
      };
      const input: Matrix = {
        height: 3,
        width: 1,
        elements: [[-1], [0], [2]]
      };

      // when
      const result: Matrix = NeuralNetworkLayerUtils.evaluate(layer, input);

      // then
      expect(result).toEqual({
        height: 2,
        width: 1,
        elements: [[5], [0]]
      });
    });
  });
});
