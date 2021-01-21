import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WebSocketSubject} from 'rxjs/internal-compatibility';

@Injectable({
  providedIn: 'root'
})
export class RandomService {

  private readonly ws: WebSocketSubject<any> = new WebSocketSubject<any>(`ws://${location.host}/api/random-generator`);

  send(): void {
    this.ws.next({range: 1024});
  }

  receive(): Observable<any> {
    return this.ws.asObservable();
  }

}
