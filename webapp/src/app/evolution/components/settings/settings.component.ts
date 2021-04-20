import {Component} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {Router} from '@angular/router';
import * as EvolutionActions from '../../state/evolution.actions';
import * as EvolutionSelectors from '../../state/evolution.selectors';
import {EvolutionState} from '../../state/evolution.state';
import {ActivationFunction, LayerSettings, Settings} from '../../model/settings';
import {take} from 'rxjs/operators';

@Component({
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.scss']
})
export class SettingsComponent {

  readonly activationFunction = ActivationFunction;

  readonly form = this.formBuilder.group({
    range: this.formBuilder.control(null, Validators.required),
    game: this.formBuilder.group({
      fieldWidth: this.formBuilder.control(null),
      fieldHeight: this.formBuilder.control(null),
      snakeEndurance: this.formBuilder.control(null),
    }),
    evolution: this.formBuilder.group({
      generationSize: this.formBuilder.control(null),
      mutationProbability: this.formBuilder.control(null),
      mutationRate: this.formBuilder.control(null),
    }),
    neuralNetwork: this.formBuilder.group({
      hiddenLayers: this.formBuilder.array([])
    })
  });

  get rangeControl(): FormControl {
    return this.form.get('range') as FormControl;
  }

  get fieldWidthControl(): FormControl {
    return this.form.get('game.fieldWidth') as FormControl;
  }

  get fieldHeightControl(): FormControl {
    return this.form.get('game.fieldHeight') as FormControl;
  }

  get enduranceControl(): FormControl {
    return this.form.get('game.snakeEndurance') as FormControl;
  }

  get generationSizeControl(): FormControl {
    return this.form.get('evolution.generationSize') as FormControl;
  }

  get mutationProbabilityControl(): FormControl {
    return this.form.get('evolution.mutationProbability') as FormControl;
  }

  get mutationRateControl(): FormControl {
    return this.form.get('evolution.mutationRate') as FormControl;
  }

  get hiddenLayersControl(): FormArray {
    return this.form.get('neuralNetwork.hiddenLayers') as FormArray;
  }

  constructor(private readonly formBuilder: FormBuilder,
              private readonly store: Store<EvolutionState>,
              private readonly router: Router) {

    this.store.select(EvolutionSelectors.selectSettings).pipe(
      take(1)
    ).subscribe((settings: Settings) => {
      if (settings) {
        this.form.patchValue(settings);
        settings.neuralNetwork.hiddenLayers.forEach((layer: LayerSettings) => {
          this.addHiddenLayer(layer);
        });
      }
    });
  }

  private createLayerControl(value?: LayerSettings): FormGroup {
    return this.formBuilder.group({
      numberOfNeurons: this.formBuilder.control(value?.numberOfNeurons),
      activationFunction: this.formBuilder.control(value?.activationFunction)
    });
  }

  addHiddenLayer(value?: LayerSettings): void {
    this.hiddenLayersControl.push(this.createLayerControl(value));
  }

  deleteHiddenLayer(index: number): void {
    this.hiddenLayersControl.removeAt(index);
  }

  getNumberOfNeurons(layer: AbstractControl): FormControl {
    return layer.get('numberOfNeurons') as FormControl;
  }

  getActivationFunction(layer: AbstractControl): FormControl {
    return layer.get('activationFunction') as FormControl;
  }

  createEvolution(): void {
    this.store.dispatch(EvolutionActions.createEvolution({
      settings: this.form.value
    }));
  }

}
