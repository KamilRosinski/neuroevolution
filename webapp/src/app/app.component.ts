import {Component} from '@angular/core';
import {RandomService} from './services/random.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  readonly message$: Observable<any>;

  constructor(private readonly randomService: RandomService) {
    this.message$ = randomService.receive();
  }

  test(): void {
    console.log('send');
    this.randomService.send();
  }
}
