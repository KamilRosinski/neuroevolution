import {Vector, VectorElementProvider} from '../vector/vector';

export type MatrixElementProvider = (row: number, column: number) => number;

export class Matrix {


  private constructor(public readonly height: number,
                      public readonly width: number,
                      public readonly elements: readonly (readonly number[])[]) {
  }

  static fromProvider(height: number, width: number, provider: MatrixElementProvider): Matrix {
    const elements: number[][] = [];
    for (let r = 0; r < height; ++r) {
      const row: number[] = [];
      for (let c = 0; c < width; ++c) {
        const element: number = provider(r, c);
        if (!element && element !== 0) {
          throw new Error('Matrix element cannot be empty.');
        }
        row.push(element);
      }
      elements.push(row);
    }
    return new Matrix(height, width, elements);
  }

  multiply(vector: Vector): Vector {
    if (this.width !== vector.length) {
      throw new Error('Cannot multiply matrix by vector. Dimensions does not match.');
    }

    const provider: VectorElementProvider = (index: number): number => {
      let result = 0;
      for (let i = 0; i < this.width; ++i) {
        result += this.elements[index][i] * vector.elements[i];
      }
      return result;
    };

    return Vector.fromProvider(this.height, provider);
  }



}
