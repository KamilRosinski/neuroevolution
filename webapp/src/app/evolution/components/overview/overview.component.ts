import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {EvolutionState, EvolutionStatus} from '../../state/evolution.state';
import {Observable} from 'rxjs';
import * as EvolutionSelectors from '../../state/evolution.selectors';
import * as EvolutionActions from '../../state/evolution.actions';
import {Generation} from '../../model/generation';
import {Settings} from '../../model/settings';

@Component({
  templateUrl: 'overview.component.html',
  styleUrls: ['overview.component.scss']
})
export class OverviewComponent {

  readonly settings$: Observable<Settings> = this.store.select(EvolutionSelectors.selectSettings);
  readonly status$: Observable<EvolutionStatus> = this.store.select(EvolutionSelectors.selectEvolutionStatus);
  readonly generations$: Observable<Generation[]> = this.store.select(EvolutionSelectors.selectGenerations);

  constructor(private readonly store: Store<EvolutionState>) {
  }

  onStart(): void {
    this.store.dispatch(EvolutionActions.startEvolution());
  }

  onStop(): void {
    this.store.dispatch(EvolutionActions.stopEvolution());
  }

  onReset(): void {
    this.store.dispatch(EvolutionActions.resetEvolution());
  }

}
