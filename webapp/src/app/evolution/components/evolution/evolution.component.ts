import {Component} from '@angular/core';
import {NewGeneration} from '../../model/new-generation';
import {EvolutionService} from '../../services/evolution.service';
import {EvolutionStarted} from '../../model/evolution-started';

@Component({
  templateUrl: 'evolution.component.html',
  styleUrls: ['evolution.component.scss']
})
export class EvolutionComponent {

  values: NewGeneration[] = [];
  evolutionId: string = '';

  constructor(private readonly evolutionService: EvolutionService) {
  }

  start(): void {
    this.values = [];
    this.evolutionService.receiveEvolutionStarted().subscribe(
      (message: EvolutionStarted) => this.evolutionId = message.evolutionId,
      (error) => {
        console.log('error', error);
      },
      () => {
        console.log('completed');
        this.evolutionId = '';
      }
    );
    this.evolutionService.receiveNewGeneration().subscribe((message: NewGeneration) => this.values.push(message));
    this.evolutionService.sendStartEvolution({range: 1024});
  }

  stop(): void {
    this.evolutionService.sendStopEvolution({evolutionId: this.evolutionId});
  }

}
