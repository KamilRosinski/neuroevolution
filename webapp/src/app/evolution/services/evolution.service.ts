import {Injectable} from '@angular/core';
import {empty, Observable, Observer, Subscriber} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';
import {ActionType} from '../model/action-type';
import {NewGeneration} from '../model/new-generation';
import {EvolutionStarted} from '../model/evolution-started';
import {StartEvolution} from '../model/start-evolution';
import {StopEvolution} from '../model/stop-evolution';
import {filter, map} from 'rxjs/operators';
import {Message} from '../model/message';
import {EvolutionSettings} from '../state/evolution.state';

@Injectable({
  providedIn: 'root'
})
export class EvolutionService {

  private readonly ws: WebSocketSubject<any> = new WebSocketSubject<any>(`ws://${location.host}/api/evolution`);

  sendStartEvolution(settings: EvolutionSettings | undefined): Observable<never> {
    return new Observable<never>(subscriber => {
      if (settings) {
        this.ws.next({
          action: ActionType.START_EVOLUTION,
          body: settings
        });
        subscriber.complete();
      } else {
        subscriber.error('Failed to send message. Settings cannot be undefined.');
      }
    });
  }

  sendStopEvolution(evolutionId: string | undefined): Observable<never> {
    return new Observable<never>(subscriber => {
      if (evolutionId) {
        this.ws.next({
          action: ActionType.STOP_EVOLUTION,
          body: evolutionId
        });
        subscriber.complete();
      } else {
        subscriber.error('Failed to send message. Evolution-ID cannot be undefined.');
      }
    });
  }

  receiveEvolutionStarted(): Observable<EvolutionStarted> {
    return this.ws.pipe(
      filter((message: Message) => message.action === ActionType.EVOLUTION_STARTED),
      map((message: Message) => message.body as EvolutionStarted)
    );
  }

  receiveNewGeneration(): Observable<NewGeneration> {
    return this.ws.pipe(
      filter((message: Message) => message.action === ActionType.NEW_GENERATION),
      map((message: Message) => message.body as NewGeneration)
    );
  }

}
