import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ErrorMessage} from '../../state/error-message.state';

@Component({
  selector: 'app-error-message',
  templateUrl: 'error-message.component.html',
  styleUrls: ['error-message.component.scss']
})
export class ErrorMessageComponent {

  @Input() errorMessage: ErrorMessage;
  @Output() readonly close: EventEmitter<void> = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
  }

}
