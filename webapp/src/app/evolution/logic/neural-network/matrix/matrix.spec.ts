import {Vector} from '../vector/vector';
import {Matrix, MatrixElementProvider} from './matrix';

describe('Matrix', () => {
  describe('create', () => {
    it('should create matrix based on provider function', () => {
      // given
      const height = 3;
      const width = 2;
      const elementProvider: MatrixElementProvider = (row: number, column: number): number => row - (2 * column);

      // when
      const result: Matrix = Matrix.fromProvider(height, width, elementProvider);

      // then
      expect(result.height).toEqual(height);
      expect(result.width).toEqual(width);
      expect(result.elements).toEqual([[0, -2], [1, -1], [2, 0]]);
    });
  });

  describe('multiply', () => {
    it('should multiply matrix by vector', () => {
      // given
      const matrix: Matrix = Matrix.fromProvider(
        2,
        3,
        (row: number, column: number): number => [[-1, 0, 2], [2, 1, -3]][row][column]
      );
      const vector: Vector = Vector.fromArray([4, -2, 3]);

      // when
      const result: Vector = matrix.multiply(vector);

      // then
      expect(result.length).toEqual(2);
      expect(result.elements).toEqual([2, -3]);
    });

    it('should throw error when matrix and vector dimensions does not match', () => {
      // given
      const matrix: Matrix = Matrix.fromProvider(2, 1, (): number => 0);
      const vector: Vector = Vector.fromProvider(2, (): number => 0);

      // when, then
      expect(() => matrix.multiply(vector)).toThrowError('Cannot multiply matrix by vector. Dimensions does not match.');
    });
  });
});
