import {Component} from '@angular/core';
import {RandomService} from './services/random.service';
import {ValueGenerated} from './model/value-generated';
import {Message} from './model/message';
import {ActionType} from './model/action-type';
import {GeneratorStarted} from './model/generator-started';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  values: ValueGenerated[] = [];
  jobId: string = '';

  private subscription: Subscription = new Subscription();

  constructor(private readonly randomService: RandomService) {
  }

  start(): void {
    this.values = [];
    this.subscription.add(this.randomService.receive().subscribe((message: Message) => {
      switch (message.action) {
        case ActionType.GENERATOR_STARTED:
          this.jobId = (message.body as GeneratorStarted).jobId;
          break;
        case ActionType.VALUE_GENERATED:
          this.values.push(message.body as ValueGenerated);
          break;
      }
    }));
    this.randomService.send({
      action: ActionType.START_GENERATOR,
      body: {
        range: 1024
      }
    });
  }

  stop(): void {
    this.subscription.unsubscribe();
    this.jobId = '';
  }

}
