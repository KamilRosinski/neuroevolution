export interface Matrix {
  readonly height: number;
  readonly width: number;
  readonly elements: readonly (readonly number[])[];
}
