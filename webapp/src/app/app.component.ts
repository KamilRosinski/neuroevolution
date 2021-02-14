import {Component} from '@angular/core';
import {EvolutionService} from './services/evolution.service';
import {NewGeneration} from './model/new-generation';
import {EvolutionStarted} from './model/evolution-started';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

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
