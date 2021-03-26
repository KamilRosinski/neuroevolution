import {Component} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {EvolutionSettings, EvolutionState} from '../../state/evolution.state';
import {Router} from '@angular/router';
import * as EvolutionActions from '../../state/evolution.actions';
import {noop} from 'rxjs';

@Component({
  templateUrl: 'create-evolution.component.html',
  styleUrls: ['create-evolution.component.scss']
})
export class CreateEvolutionComponent {

  readonly form = this.formBuilder.group({
    range: this.formBuilder.control(null, Validators.required)
  });

  get rangeControl(): FormControl {
    return this.form.get('range') as FormControl;
  }

  constructor(private readonly formBuilder: FormBuilder,
              private readonly store: Store<EvolutionState>,
              private readonly router: Router) {
  }

  createEvolution(): void {
    const settings: EvolutionSettings = {
      range: this.rangeControl.value
    };
    this.store.dispatch(EvolutionActions.createEvolution({settings}));
    this.router.navigate(['evolution', 'overview']).then(noop);
  }

}
