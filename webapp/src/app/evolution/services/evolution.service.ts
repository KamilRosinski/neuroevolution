import {Injectable} from '@angular/core';
import {merge, Observable, Observer, Subject} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';
import {Generation} from '../model/generation';
import {webSocket} from 'rxjs/webSocket';
import {filter, map, take} from 'rxjs/operators';
import {Settings} from '../model/settings';

@Injectable({
  providedIn: 'root'
})
export class EvolutionService {

  private readonly closeSubject: Subject<CloseEvent> = new Subject<CloseEvent>();

  private readonly webSocket: WebSocketSubject<any> = webSocket({
    url: `ws://${location.host}/api/evolution`,
    closeObserver: this.closeSubject
  });

  private readonly errors$: Observable<never> = this.closeSubject.pipe(
    take(1),
    filter((closeEvent: CloseEvent) => closeEvent.code !== 1000),
    map((closeEvent: CloseEvent) => {
      throw new Error(closeEvent.reason);
    })
  );

  readonly generations$: Observable<Generation> = merge(
    this.webSocket,
    this.errors$
  );

  startEvolution(settings: Settings): Observable<never> {
    return new Observable<never>((observer: Observer<never>) => {
      this.webSocket.next(settings);
      observer.complete();
    });
  }

  stopEvolution(): Observable<never> {
    return new Observable<never>((observer: Observer<never>) => {
      this.webSocket.complete();
      observer.complete();
    });
  }

}
