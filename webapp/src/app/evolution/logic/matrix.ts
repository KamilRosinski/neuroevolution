export interface Matrix {
  readonly height: number;
  readonly width: number;
  readonly elements: ReadonlyArray<ReadonlyArray<number>>;
}
