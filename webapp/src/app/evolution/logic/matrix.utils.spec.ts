import {Matrix} from './matrix';
import {ElementProvider, MatrixUtils} from './matrix.utils';

describe('MatrixUtils', () => {
  describe('create', () => {
    it('should create matrix', () => {
      // given
      const matrix: Matrix = {
        height: 2,
        width: 3,
        elements: [[0, 1, 2], [3, 4, 5]]
      };

      // when
      const result: MatrixUtils = MatrixUtils.createMatrix(matrix);

      // then
      expect(result.matrix).toEqual(matrix);
    });

    it('should create column vector', () => {
      // given
      const elements: number[] = [1, -2, 3];

      // when
      const result: MatrixUtils = MatrixUtils.createColumnVector(...elements);

      // then
      expect(result.matrix).toEqual({
        height: elements.length,
        width: 1,
        elements: [[elements[0]], [elements[1]], [elements[2]]]
      });
    });

    it('should create matrix based on provider function', () => {
      // given
      const height = 3;
      const width = 2;
      const elementProvider: ElementProvider = (row: number, column: number): number => row - (2 * column);

      // when
      const result: MatrixUtils = MatrixUtils.createMatrixFromProvider(height, width, elementProvider);

      // then
      expect(result.matrix).toEqual({
        height,
        width,
        elements: [[0, -2], [1, -1], [2, 0]]
      });
    });
  });

  describe('scale', () => {
    it('should scale vector', () => {
      // given
      const factor = -2;
      const utils: MatrixUtils = MatrixUtils.createMatrix({
        height: 2,
        width: 3,
        elements: [[-1, 0, 2], [0, 1, -3]]
      });

      // when
      const result: MatrixUtils = utils.scale(factor);

      // then
      expect(result.matrix).toEqual({
        ...utils.matrix,
        elements: [[2, -0, -4], [-0, -2, 6]]
      })
    });
  });

  describe('multiply', () => {

  });

  describe('add', () => {

  });

  describe('apply', () => {

  });
});
