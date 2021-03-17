import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {AppState, ErrorMessage} from '../../state/app.state';
import {Observable, of} from 'rxjs';
import * as AppSelectors from '../../state/app.selectors';
import * as AppActions from '../../state/app.actions';

@Component({
  selector: 'app-error-messages',
  templateUrl: 'error-messages.component.html',
  styleUrls: ['error-messages.component.scss']
})
export class ErrorMessagesComponent {

  readonly errorMessages$: Observable<ErrorMessage[]> = this.store.select(AppSelectors.selectAllErrorMessages);

  constructor(private readonly store: Store<AppState>) {
  }

  closeErrorMessage(id: number): void {
    this.store.dispatch(AppActions.deleteErrorMessage({id}));
  }

}
