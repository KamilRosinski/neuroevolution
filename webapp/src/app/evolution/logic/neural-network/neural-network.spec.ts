import {NeuralNetwork} from './neural-network';
import {ActivationFunction} from './activation-function/activation-function';
import {NeuralNetworkSettings} from './settings/neural-network-settings';
import {Vector} from './vector/vector';

describe('NeuralNetworkUtils', () => {
  describe('create', () => {
    it('should create neural network based on settings', () => {
      // given
      const settings: NeuralNetworkSettings = {
        inputSize: 2,
        layers: [
          {
            numberOfNeurons: 3,
            activationFunction: ActivationFunction.RE_LU
          },
          {
            numberOfNeurons: 1,
            activationFunction: ActivationFunction.SIGMOID
          }
        ]
      };
      const elements: Iterator<number> = [-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6][Symbol.iterator]();

      // when
      const result: NeuralNetwork = new NeuralNetwork(settings, elements);

      // then
      expect(result.layers.length).toEqual(2);
      expect(result.layers[0].weights.elements).toEqual([[-6, -5], [-4, -3], [-2, -1]]);
      expect(result.layers[0].biases.elements).toEqual([0, 1, 2]);
      expect(result.layers[1].weights.elements).toEqual([[3, 4, 5]]);
      expect(result.layers[1].biases.elements).toEqual([6]);
    });
  });

  describe('evaluate', () => {
    it('should evaluate neural network output value', () => {
      // given
      const arr: number[] = [0, 2, -1, -3, 2, 1, -2, 1, 3, 2, 3, -1, -1];
      const elements: Iterator<number> = arr[Symbol.iterator]();
      const settings: NeuralNetworkSettings = {
        inputSize: 2,
        layers: [
          {
            numberOfNeurons: 3,
            activationFunction: ActivationFunction.RE_LU
          },
          {
            numberOfNeurons: 1,
            activationFunction: ActivationFunction.SIGMOID
          }
        ]
      };
      const network: NeuralNetwork = new NeuralNetwork(settings, elements);
      const input: Vector = Vector.fromArray([-1, 2]);

      // when
      const result: Vector = network.evaluate(input);

      // then
      expect(result.elements).toEqual([0.5]);
    });
  });
});
