export class Matrix {

  private readonly values: number[] = [];

  private constructor(public readonly height: number,
                      public readonly width: number,
                      valueProvider: (row: number, column: number) => number) {
    for (let column = 0; column < width; ++column) {
      for (let row = 0; row < height; ++row) {
        this.values.push(valueProvider(row, column));
      }
    }
  }

  static fromValues(values: number[]): Matrix {
    return new Matrix(values.length, 1, (row: number, _: number): number => values[row]);
  }

  get(row: number, column: number = 0): number {
    if (row < 0 || row >= this.height || column < 0 || column >= this.width) {
      throw new Error('Cannot get matrix element.');
    }
    return this.values[row * this.width + column];
  }

  add(other: Matrix): Matrix {
    if (this.height !== other.height || this.width !== other.width) {
      throw new Error('Cannot add matrices. Dimensions does not match.');
    }
    return new Matrix(this.height, this.width, (row: number, column: number): number => this.get(row, column) + other.get(row, column));
  }

  multiply(other: Matrix): Matrix {
    if (this.width !== other.height) {
      throw new Error('Cannot multiply matrices. Dimensions does not match.');
    }

    return new Matrix(this.height, other.width, (row: number, column: number): number => {
      let result = 0;
      for (let i = 0; i < this.width; ++i) {
        result += this.get(row, i) * other.get(i, column);
      }
      return result;
    });
  }

  scale(factor: number): Matrix {
    return new Matrix(this.height, this.width, (row: number, column: number): number => this.get(row, column) * factor);
  }

  apply(valueModifier: (value: number) => number): Matrix {
    return new Matrix(this.height, this.width, (row: number, column: number): number => valueModifier(this.get(row, column)));
  }

}
