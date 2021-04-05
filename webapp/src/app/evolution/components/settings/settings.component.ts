import {Component} from '@angular/core';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {Router} from '@angular/router';
import * as EvolutionActions from '../../state/evolution.actions';
import * as EvolutionSelectors from '../../state/evolution.selectors';
import {EvolutionState} from '../../state/evolution.state';
import {Settings} from '../../model/settings';
import {take} from 'rxjs/operators';

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

    this.store.select(EvolutionSelectors.selectSettings).pipe(
      take(1)
    ).subscribe((settings: Settings) => {
      if (settings) {
        this.form.setValue(settings);
      }
    });
  }

  createEvolution(): void {
    const settings: Settings = this.form.value;
    this.store.dispatch(EvolutionActions.createEvolution({settings}));
    this.router.navigate(['evolution', 'overview']).then();
  }

}
