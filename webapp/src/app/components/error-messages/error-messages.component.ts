import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {ErrorMessageState, ErrorMessage} from '../../state/error-message.state';
import {Observable, of} from 'rxjs';
import * as ErrorMessageSelectors from '../../state/error-message.selectors';
import * as ErrorMessageActions from '../../state/error-message.actions';

@Component({
  selector: 'app-error-messages',
  templateUrl: 'error-messages.component.html',
  styleUrls: ['error-messages.component.scss']
})
export class ErrorMessagesComponent {

  readonly errorMessages$: Observable<ErrorMessage[]> = this.store.select(ErrorMessageSelectors.selectAllErrorMessages);

  constructor(private readonly store: Store<ErrorMessageState>) {
  }

  closeErrorMessage(id: number): void {
    this.store.dispatch(ErrorMessageActions.deleteErrorMessage({id}));
  }

}
