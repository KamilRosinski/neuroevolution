import { Component } from '@angular/core';
import {NeuroevolutionService} from './services/neuroevolution.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  text: string = '';

  constructor(private readonly neuroevolutionService: NeuroevolutionService) {
  }

  test(): void {
    this.neuroevolutionService.test().subscribe((response: string) => {
      this.text = response;
    });
  }

}
