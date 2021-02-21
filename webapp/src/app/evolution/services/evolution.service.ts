import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';
import {ActionType} from '../model/action-type';
import {NewGeneration} from '../model/new-generation';
import {EvolutionStarted} from '../model/evolution-started';
import {StartEvolution} from '../model/start-evolution';
import {StopEvolution} from '../model/stop-evolution';
import {filter, map} from 'rxjs/operators';
import {Message} from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class EvolutionService {

  private readonly ws: WebSocketSubject<any> = new WebSocketSubject<any>(`ws://${location.host}/api/evolution`);

  sendStartEvolution(message: StartEvolution): void {
    this.ws.next({
      action: ActionType.START_EVOLUTION,
      body: message
    });
  }

  sendStopEvolution(message: StopEvolution): void {
    this.ws.next({
      action: ActionType.STOP_EVOLUTION,
      body: message
    });
  }

  receiveMessage(): Observable<Message> {
    return this.ws.asObservable();
  }

  // receiveEvolutionStarted(): Observable<EvolutionStarted> {
  //   return this.ws.pipe(
  //     filter(message => message.action === ActionType.EVOLUTION_STARTED),
  //     map(message => message.body)
  //   );
  // }

  // receiveNewGeneration(): Observable<NewGeneration> {
  //   return this.ws.pipe(
  //     filter(message => message.action === ActionType.NEW_GENERATION),
  //     map(message => message.body)
  //   );
  // }

}
