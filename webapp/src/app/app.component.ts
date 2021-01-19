import {Component} from '@angular/core';
import {RandomService} from './services/random.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  jobId: string = '';

  constructor(private readonly randomService: RandomService) {
  }

  start(): void {
    this.randomService.startGenerator(1024).subscribe((response: { jobId: string }) => {
      this.jobId = response.jobId;
    });
  }

  stop(): void {
    this.randomService.stopGenerator(this.jobId).subscribe(() => {
      this.jobId = '';
    });
  }

}
