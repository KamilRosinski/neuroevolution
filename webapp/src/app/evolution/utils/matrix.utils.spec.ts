import {Matrix} from '../model/matrix';
import * as MatrixUtils from './matrix.utils';
import {ElementModifier, ElementProvider} from './matrix.utils';

describe('MatrixUtils', () => {
  describe('create', () => {
    it('should create column vector', () => {
      // given
      const elements: number[] = [1, -2, 3];

      // when
      const result: Matrix = MatrixUtils.createColumn(...elements);

      // then
      expect(result).toEqual({
        height: elements.length,
        width: 1,
        elements: [[elements[0]], [elements[1]], [elements[2]]]
      });
    });

    it('should create matrix based on provider function', () => {
      // given
      const elementProvider: ElementProvider = (row: number, column: number): number => row - (2 * column);

      // when
      const result: Matrix = MatrixUtils.createFromProvider(3, 2, elementProvider);

      // then
      expect(result).toEqual({
        height: 3,
        width: 2,
        elements: [[0, -2], [1, -1], [2, 0]]
      });
    });
  });

  describe('scale', () => {
    it('should scale vector', () => {
      // given
      const matrix: Matrix = {
        height: 2,
        width: 3,
        elements: [[-1, 0, 2], [0, 1, -3]]
      };
      const factor = -2;

      // when
      const result: Matrix = MatrixUtils.scale(matrix, factor);

      // then
      expect(result).toEqual({
        ...matrix,
        elements: [[2, -0, -4], [-0, -2, 6]]
      });
    });
  });

  describe('multiply', () => {
    it('should multiply 2 matrices', () => {
      // given
      const m1: Matrix = {
        height: 2,
        width: 3,
        elements: [[-1, 0, 2], [2, 1, -3]]
      };
      const m2: Matrix = {
        height: 3,
        width: 1,
        elements: [[4], [-2], [3]]
      };

      // when
      const result: Matrix = MatrixUtils.multiply(m1, m2);

      // then
      expect(result).toEqual({
        height: 2,
        width: 1,
        elements: [[2], [-3]]
      });
    });

    it('should throw error when matrix dimensions does not match', () => {
      // given
      const m1: Matrix = {
        height: 2,
        width: 1,
        elements: [[0], [0]]
      };
      const m2: Matrix = {
        height: 2,
        width: 2,
        elements: [[0, 0], [0, 0]]
      };

      // when, then
      expect(() => MatrixUtils.multiply(m1, m2)).toThrowError('Cannot multiply matrices. Dimensions does not match.');
    });
  });

  describe('add', () => {
    it('should add 2 matrices', () => {
      // given
      const m1: Matrix = {
        height: 2,
        width: 3,
        elements: [[0, 1, -2], [-1, 3, 1]]
      };
      const m2: Matrix = {
        height: 2,
        width: 3,
        elements: [[2, -3, 1], [0, 0, 2]]
      };

      // when
      const result: Matrix = MatrixUtils.add(m1, m2);

      // then
      expect(result).toEqual({
        height: 2,
        width: 3,
        elements: [[2, -2, -1], [-1, 3, 3]]
      });
    });

    it('should throw error when trying to add matrices of different height', () => {
      // given
      const m1: Matrix = {
        height: 1,
        width: 2,
        elements: [[0, 1]]
      };
      const m2: Matrix = {
        height: 2,
        width: 2,
        elements: [[1, 0], [0, 1]]
      };

      // when, then
      expect(() => MatrixUtils.add(m1, m2)).toThrowError('Cannot add matrices. Dimensions does not match.');
    });

    it('should throw error when trying to add matrices of different width', () => {
      // given
      const m1: Matrix = {
        height: 2,
        width: 1,
        elements: [[0], [1]]
      };
      const m2: Matrix = {
        height: 2,
        width: 2,
        elements: [[1, 0], [0, 1]]
      };

      // when, then
      expect(() => MatrixUtils.add(m1, m2)).toThrowError('Cannot add matrices. Dimensions does not match.');
    });
  });

  describe('apply', () => {
    it('should apply function to all matrix\'s elements', () => {
      // given
      const matrix: Matrix = {
        height: 2,
        width: 3,
        elements: [[5, -4, 0], [2, 3, -1]]
      };
      const elementModifier: ElementModifier = (element: number): number => 2 * element - 3;

      // when
      const result: Matrix = MatrixUtils.apply(matrix, elementModifier);

      // then
      expect(result).toEqual({
        ...matrix,
        elements: [[7, -11, -3], [1, 3, -5]]
      });
    });
  });
});
