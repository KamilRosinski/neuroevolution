import {Vector, VectorElementModifier, VectorElementProvider} from './vector';

describe('Vector', () => {
  describe('create', () => {
    it('should create vector based on provider', () => {
      // given
      const length = 3;
      const provider: VectorElementProvider = (index: number): number => 2 * index - 1;

      // when
      const result: Vector = Vector.fromProvider(length, provider);

      // then
      expect(result.length).toEqual(length);
      expect(result.elements).toEqual([-1, 1, 3]);
    });

    it('should create vector based on array', () => {
      // given
      const array: number[] = [2, -3, 0];

      // when
      const result: Vector = Vector.fromArray(array);

      // then
      expect(result.length).toEqual(array.length);
      expect(result.elements).toEqual(array);
    });
  });

  describe('apply', () => {
    it('apply function to all vector\'s elements', () => {
      // given
      const vector: Vector = Vector.fromArray([1, 0, -2, 3]);
      const modifier: VectorElementModifier = (value: number): number => value * value;

      // when
      const result: Vector = vector.apply(modifier);

      // then
      expect(result.elements).toEqual([1, 0, 4, 9]);
    });
  });

  describe('add', () => {
    it('should add 2 vectors', () => {
      // given
      const v1: Vector = Vector.fromArray([0, 1, 2]);
      const v2: Vector = Vector.fromArray([-1, 2, -3]);

      // when
      const result: Vector = v1.add(v2);

      // then
      expect(result.elements).toEqual([-1, 3, -1]);
    });

    it('should throw exception when trying to add vectors of unequal lengths', () => {
      // given
      const v1: Vector = Vector.fromArray([1, 2]);
      const v2: Vector = Vector.fromArray([0]);

      // when, then
      expect(() => v1.add(v2)).toThrowError('Cannot add vectors. Lengths does not match.');
    });
  });

});
