import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {EvolutionState, EvolutionStatus} from '../../store/evolution.state';
import {Observable} from 'rxjs';
import * as EvolutionSelectors from '../../store/evolution.selectors';
import * as EvolutionActions from '../../store/evolution.actions';

@Component({
  templateUrl: 'evolution.component.html',
  styleUrls: ['evolution.component.scss']
})
export class EvolutionComponent {

  readonly evolutionStatus = EvolutionStatus;

  readonly status$: Observable<EvolutionStatus> = this.store.select(EvolutionSelectors.selectEvolutionStatus);
  readonly evolutionId$: Observable<string> = this.store.select(EvolutionSelectors.selectEvolutionId);
  readonly generations$: Observable<{ id: number, score: number }[]> = this.store.select(EvolutionSelectors.selectGenerations);

  constructor(private readonly store: Store<EvolutionState>) {
  }

  start(): void {
    this.store.dispatch(EvolutionActions.scheduleEvolution({
      range: 1024
    }));
  }

  stop(): void {
    this.store.dispatch(EvolutionActions.stopEvolution());
  }

}
