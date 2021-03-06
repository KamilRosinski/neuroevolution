import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {EvolutionSettings, EvolutionState, EvolutionStatus} from '../../store/evolution.state';
import {Observable} from 'rxjs';
import * as EvolutionSelectors from '../../store/evolution.selectors';
import * as EvolutionActions from '../../store/evolution.actions';

@Component({
  templateUrl: 'evolution-overview.component.html',
  styleUrls: ['evolution-overview.component.scss']
})
export class EvolutionOverviewComponent {

  readonly settings$: Observable<EvolutionSettings | undefined> = this.store.select(EvolutionSelectors.selectSettings);
  readonly status$: Observable<EvolutionStatus> = this.store.select(EvolutionSelectors.selectEvolutionStatus);
  readonly evolutionId$: Observable<string | undefined> = this.store.select(EvolutionSelectors.selectEvolutionId);
  readonly generations$: Observable<{ id: number, score: number }[]> = this.store.select(EvolutionSelectors.selectGenerations);

  constructor(private readonly store: Store<EvolutionState>) {
  }

  start(): void {
    this.store.dispatch(EvolutionActions.scheduleEvolution());
  }

  stop(): void {
    this.store.dispatch(EvolutionActions.stopEvolution());
  }

}
