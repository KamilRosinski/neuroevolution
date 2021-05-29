import {Matrix} from './matrix';

export type ElementProvider = (row: number, column: number) => number;
export type ElementModifier = (element: number) => number;

export class MatrixUtils {

  static createColumnVector(...elements: number[]): MatrixUtils {
    return new MatrixUtils({
      height: elements.length,
      width: 1,
      elements: elements.map((element: number): number[] => [element])
    });
  }

  static createMatrix(matrix: Matrix): MatrixUtils {
    return new MatrixUtils(matrix);
  }

  static createMatrixFromProvider(height: number, width: number, elementProvider: ElementProvider): MatrixUtils {
    const elements: number[][] = [];
    for (let r = 0; r < height; ++r) {
      const row: number[] = [];
      for (let c = 0; c < width; ++c) {
        row.push(elementProvider(r, c));
      }
      elements.push(row);
    }

    return new MatrixUtils({
      height,
      width,
      elements
    });
}

  private constructor(public readonly matrix: Matrix) {
  }

  scale(factor: number): MatrixUtils {
    return MatrixUtils.createMatrixFromProvider(
      this.matrix.height,
      this.matrix.width,
      (row: number, column: number): number => this.matrix.elements[row][column] * factor
    );
  }

  apply(elementModifier: ElementModifier): MatrixUtils {
    return MatrixUtils.createMatrixFromProvider(
      this.matrix.height,
      this.matrix.width,
      (row: number, column: number): number => elementModifier(this.matrix.elements[row][column])
    );
  }

  add(other: Matrix): MatrixUtils {
    if (this.matrix.height !== other.height || this.matrix.width !== other.width) {
      throw new Error('Cannot add matrices. Dimensions does not match.');
    }

    return MatrixUtils.createMatrixFromProvider(
      this.matrix.height,
      this.matrix.width,
      (row: number, column: number): number => this.matrix.elements[row][column] + other.elements[row][column]
    );
  }

  multiply(other: Matrix): MatrixUtils {
    if (this.matrix.width !== other.height) {
      throw new Error('Cannot multiply matrices. Dimensions does not match.');
    }

    return MatrixUtils.createMatrixFromProvider(
      this.matrix.height,
      this.matrix.width,
      (row: number, column: number): number => {
        let result = 0;
        for (let i = 0; i < this.matrix.width; ++i) {
          result += this.matrix.elements[row][i] * other.elements[i][column];
        }
        return result;
      }
    );
  }

}
