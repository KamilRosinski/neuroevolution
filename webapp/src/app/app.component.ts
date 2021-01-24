import {Component} from '@angular/core';
import {RandomService} from './services/random.service';
import {ValueGenerated} from './model/value-generated';
import {Message} from './model/message';
import {ActionType} from './model/action-type';
import {GeneratorStarted} from './model/generator-started';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  values: ValueGenerated[] = [];
  jobId: string = '';

  constructor(private readonly randomService: RandomService) {
    randomService.receive().subscribe((message: Message) => {
      switch (message.action) {
        case ActionType.GENERATOR_STARTED:
          this.jobId = (message.body as GeneratorStarted).jobId;
          break;
        case ActionType.VALUE_GENERATED:
          this.values.push(message.body as ValueGenerated);
          break;
      }
    });
  }

  start(): void {
    this.values = [];
    this.randomService.send({
      action: ActionType.START_GENERATOR,
      body: {
        range: 1024
      }
    });
  }

  stop(): void {
    this.randomService.send({
      action: ActionType.STOP_GENERATOR,
      body: {
        jobId: this.jobId
      }
    });
    this.jobId = null;
  }

}
