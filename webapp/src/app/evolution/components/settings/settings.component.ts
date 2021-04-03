import {Component} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {Router} from '@angular/router';
import * as EvolutionActions from '../../state/evolution.actions';
import {noop} from 'rxjs';
import {EvolutionState} from '../../state/evolution.state';
import {Settings} from '../../model/settings';

@Component({
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.scss']
})
export class SettingsComponent {

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
    const settings: Settings = {
      range: this.rangeControl.value
    };
    this.store.dispatch(EvolutionActions.createEvolution({settings}));
    this.router.navigate(['evolution', 'overview']).then(noop);
  }

}
