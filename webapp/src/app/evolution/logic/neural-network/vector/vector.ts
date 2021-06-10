export type VectorElementProvider = (index: number) => number;
export type VectorElementModifier = (element: number) => number;

export class Vector {

  get length(): number {
    return this.elements.length;
  }

  private constructor(public readonly elements: readonly number[]) {
  }

  static fromProvider(length: number, provider: VectorElementProvider): Vector {
    const elements: number[] = [];
    for (let i = 0; i < length; ++i) {
      const element: number = provider(i);
      if (!element && element !== 0) {
        throw new Error('Vector element cannot be empty.');
      }
      elements.push(element);
    }
    return new Vector(elements);
  }

  static fromArray(elements: readonly number[]): Vector {
    return new Vector(elements);
  }

  apply(modifier: VectorElementModifier): Vector {
    return Vector.fromProvider(
      this.length,
      (index: number): number => modifier(this.elements[index])
    );
  }

  add(vector: Vector): Vector {
    if (this.length !== vector.length) {
      throw new Error('Cannot add vectors. Lengths does not match.');
    }

    return Vector.fromProvider(
      this.length,
      (index: number): number => this.elements[index] + vector.elements[index]
    );
  }

}
