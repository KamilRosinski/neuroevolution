import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SequenceGeneratorService {

  private currentValue: number = 0;

  generate(): number {
    return this.currentValue++;
  }

}
