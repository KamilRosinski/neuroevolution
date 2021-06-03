import {ActivationFunction} from '../activation-function/activation-function';

export interface NeuralNetworkLayerSettings {
    readonly numberOfNeurons: number;
    readonly activationFunction: ActivationFunction;
}
