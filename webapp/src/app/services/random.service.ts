import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RandomService {

  constructor(private readonly http: HttpClient) {
  }

  startGenerator(range: number): Observable<{jobId: string}> {
    return this.http.post<{jobId: string}>('/api/random/start', {range});
  }

  stopGenerator(jobId: string): Observable<void> {
    return this.http.post<void>('/api/random/stop', {jobId});
  }

}
