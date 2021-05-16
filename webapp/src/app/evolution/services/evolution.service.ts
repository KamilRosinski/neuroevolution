import {Injectable} from '@angular/core';
import {Observable, Observer, Subject} from 'rxjs';
import {Settings} from '../model/settings';
import {Generation} from '../model/generation';

@Injectable({
  providedIn: 'root'
})
export class EvolutionService {

  private readonly worker: Worker = new Worker('../workers/evolution.worker', {
    name: 'evolution',
    type: 'module'
  });

  readonly generations$: Observable<Generation> = new Observable<Generation>((observer: Observer<Generation>) => {
    this.worker.addEventListener('message', (event: MessageEvent<Generation>) => {
      observer.next(event.data);
    });
  });

  startEvolution(settings: Settings): Observable<never> {
    return new Observable<never>((observer: Observer<never>) => {
      this.worker.postMessage(settings);
      observer.complete();
    });
  }

  stopEvolution(): Observable<never> {
    return new Observable<never>((observer: Observer<never>) => {
      this.worker.terminate();
      observer.complete();
    });
  }

}
