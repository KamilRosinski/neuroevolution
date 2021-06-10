import {NeuralNetworkLayer} from './neural-network-layer';
import {Matrix} from './matrix/matrix';
import {ActivationFunction} from './activation-function/activation-function';
import {Vector} from './vector/vector';

describe('NeuralNetworkLayerUtils', () => {
  describe('evaluate', () => {
    it('should evaluate neural network layer', () => {
      // given
      const layer: NeuralNetworkLayer = new NeuralNetworkLayer(
        Matrix.fromProvider(2, 3, ((row: number, column: number): number => [[1, 0, 2], [-2, 1, -3]][row][column])),
        Vector.fromArray([2, 1]),
        ActivationFunction.RE_LU
      );
      const input: Vector = Vector.fromArray([-1, 0, 2]);

      // when
      const result: Vector = layer.evaluate(input);

      // then
      expect(result.elements).toEqual([5, 0]);
    });
  });
});
