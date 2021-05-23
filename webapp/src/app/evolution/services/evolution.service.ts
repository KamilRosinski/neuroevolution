import {Injectable} from '@angular/core';
import {Observable, Observer, Subject, Subscriber} from 'rxjs';
import {Settings} from '../model/settings';
import {Generation} from '../model/generation';
import {finalize, takeUntil} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EvolutionService {

  private readonly terminationSubject: Subject<void> = new Subject<void>();
  private worker: Worker = null;

  startEvolution(settings: Settings): Observable<Generation> {
    return new Observable<Generation>((observer: Subscriber<Generation>) => {
      if (this.worker) {
        observer.error({
          message: 'Evolution worker was already created.'
        });
      }
      this.worker = new Worker('../workers/evolution.worker', {
        name: 'evolution',
        type: 'module'
      });
      this.worker.addEventListener('message', (event: MessageEvent<Generation>) => {
        observer.next(event.data);
      });
      this.worker.postMessage(settings);
    }).pipe(
      takeUntil(this.terminationSubject),
      finalize(() => {
        if (this.worker) {
          this.worker.terminate();
          this.worker = null;
        }
      })
    );
  }

  stopEvolution(): void {
    console.log('stop!');
    this.terminationSubject.next();
  }

}
