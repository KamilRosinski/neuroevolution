import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';
import {Message} from '../model/message';

@Injectable({
  providedIn: 'root'
})
export class RandomService {

  private readonly ws: WebSocketSubject<any> = new WebSocketSubject<any>(`ws://${location.host}/api/random-generator`);

  send(message: Message): void {
    this.ws.next(message);
  }

  receive(): Observable<Message> {
    return this.ws.asObservable();
  }

}
