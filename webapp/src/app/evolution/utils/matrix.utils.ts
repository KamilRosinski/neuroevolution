import {Matrix} from '../model/matrix';

export type ElementProvider = (row: number, column: number) => number;
export type ElementModifier = (element: number) => number;

export const createColumn = (...elements: number[]): Matrix => ({
  height: elements.length,
  width: 1,
  elements: elements.map((element: number): number[] => [element])
});

export const createFromProvider = (height: number, width: number, elementProvider: ElementProvider): Matrix => {
  const elements: number[][] = [];
  for (let r = 0; r < height; ++r) {
    const row: number[] = [];
    for (let c = 0; c < width; ++c) {
      row.push(elementProvider(r, c));
    }
    elements.push(row);
  }

  return {
    height,
    width,
    elements
  };
};

export const scale = (matrix: Matrix, factor: number): Matrix => createFromProvider(
  matrix.height,
  matrix.width,
  (row: number, column: number): number => matrix.elements[row][column] * factor
);

export const apply = (matrix: Matrix, elementModifier: ElementModifier): Matrix => createFromProvider(
  matrix.height,
  matrix.width,
  (row: number, column: number): number => elementModifier(matrix.elements[row][column])
);

export const add = (matrix1: Matrix, matrix2: Matrix): Matrix => {
  if (matrix1.height !== matrix2.height || matrix1.width !== matrix2.width) {
    throw new Error('Cannot add matrices. Dimensions does not match.');
  }

  return createFromProvider(
    matrix1.height,
    matrix1.width,
    (row: number, column: number): number => matrix1.elements[row][column] + matrix2.elements[row][column]
  );
};

export const multiply = (matrix1: Matrix, matrix2: Matrix): Matrix => {
  if (matrix1.width !== matrix2.height) {
    throw new Error('Cannot multiply matrices. Dimensions does not match.');
  }

  return createFromProvider(
    matrix1.height,
    matrix2.width,
    (row: number, column: number): number => {
      let result = 0;
      for (let i = 0; i < matrix1.width; ++i) {
        result += matrix1.elements[row][i] * matrix2.elements[i][column];
      }
      return result;
    }
  );
};
