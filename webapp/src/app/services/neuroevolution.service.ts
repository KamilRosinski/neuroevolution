import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NeuroevolutionService {

  constructor(private readonly http: HttpClient) {
  }

  test(): Observable<string> {
    return this.http.get<string>('/api/neuroevolution/test');
  }

}
