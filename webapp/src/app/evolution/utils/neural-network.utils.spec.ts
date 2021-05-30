import {Matrix} from '../model/matrix';
import * as NeuralNetworkUtils from './neural-network.utils';
import {NeuralNetwork} from '../model/neural-network';

describe('neural network utils', () => {
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
            activationFunction: (value: number): number => Math.max(0, value)
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
              elements: [[3]]
            },
            activationFunction: (value: number): number => Math.max(0, value)
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
        elements: [[4]]
      });
    });
  });
});
