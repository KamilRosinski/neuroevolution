import {Matrix} from './matrix/matrix';
import * as NeuralNetworkUtils from './neural-network.utils';
import {NeuralNetwork} from './neural-network';
import {ActivationFunction} from './activation-function/activation-function';

describe('NeuralNetworkUtils', () => {
  describe('evaluate', () => {
    it('should evaluate neural network output value', () => {
      // given
      const network: NeuralNetwork = {
        layers: [
          {
            weights: {
              height: 3,
              width: 2,
              elements: [[0, 2], [-1, -3], [2, 1]]
            },
            biases: {
              height: 3,
              width: 1,
              elements: [[-2], [1], [3]]
            },
            activationFunction: ActivationFunction.RE_LU
          },
          {
            weights: {
              height: 1,
              width: 3,
              elements: [[2, 3, -1]]
            },
            biases: {
              height: 1,
              width: 1,
              elements: [[-1]]
            },
            activationFunction: ActivationFunction.SIGMOID
          }
        ]
      };
      const input: Matrix = {
        height: 2,
        width: 1,
        elements: [[-1], [2]]
      };

      // when
      const result: Matrix = NeuralNetworkUtils.evaluate(network, input);

      // then
      expect(result).toEqual({
        height: 1,
        width: 1,
        elements: [[0.5]]
      });
    });
  });
});
