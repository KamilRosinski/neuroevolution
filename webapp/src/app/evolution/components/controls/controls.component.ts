import {Component, EventEmitter, Input, Output} from '@angular/core';
import {EvolutionStatus} from '../../state/evolution.state';

@Component({
  selector: 'evo-controls',
  templateUrl: 'controls.component.html',
  styleUrls: ['controls.component.scss']
})
export class ControlsComponent {

  @Input() status: EvolutionStatus;
  @Output() readonly start: EventEmitter<void> = new EventEmitter<void>();
  @Output() readonly stop: EventEmitter<void> = new EventEmitter<void>();
  @Output() readonly reset: EventEmitter<void> = new EventEmitter<void>();

  get isStartable(): boolean {
    return this.status === EvolutionStatus.NEW;
  }

  get isStoppable(): boolean {
    return this.status === EvolutionStatus.RUNNING;
  }

  get isResettable(): boolean {
    return this.status === EvolutionStatus.FINISHED;
  }

  onStart(): void {
    this.start.emit();
  }

  onStop(): void {
    this.stop.emit();
  }

  onReset(): void {
    this.reset.emit();
  }

}
