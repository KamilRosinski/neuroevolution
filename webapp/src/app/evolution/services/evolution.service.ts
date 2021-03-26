import {Injectable} from '@angular/core';
import {merge, Observable, Subject} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';
import {Generation} from '../model/generation';
import {EvolutionSettings} from '../state/evolution.state';
import {webSocket} from 'rxjs/webSocket';
import {filter, map, take} from 'rxjs/operators';

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

  startEvolution(settings?: EvolutionSettings): void {
    this.webSocket.next(settings);
  }

  stopEvolution(): void {
    this.webSocket.complete();
  }

}
