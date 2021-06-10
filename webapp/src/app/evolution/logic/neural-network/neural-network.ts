import {NeuralNetworkLayer} from './neural-network-layer';
import {NeuralNetworkSettings} from './settings/neural-network-settings';
import {NeuralNetworkLayerSettings} from './settings/neural-network-layer-settings';
import {Matrix} from './matrix/matrix';
import {Vector} from './vector/vector';

export class NeuralNetwork {

  readonly layers: readonly NeuralNetworkLayer[];

  constructor(settings: NeuralNetworkSettings, elements: Iterator<number>) {
    this.layers = settings.layers.map((layer: NeuralNetworkLayerSettings, index: number) => new NeuralNetworkLayer(
      Matrix.fromProvider(
        layer.numberOfNeurons,
        index === 0 ? settings.inputSize : settings.layers[index - 1].numberOfNeurons,
        (): number => elements.next().value
      ),
      Vector.fromProvider(
        layer.numberOfNeurons,
        (): number => elements.next().value
      ),
      layer.activationFunction
    ));
  }

  evaluate(input: Vector): Vector {
    return this.layers.reduce((accumulator: Vector, layer: NeuralNetworkLayer): Vector => layer.evaluate(accumulator), input);
  }

}
