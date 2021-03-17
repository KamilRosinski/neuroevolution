import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ErrorMessage} from '../../state/app.state';

@Component({
  selector: 'app-error-message',
  templateUrl: 'error-message.component.html',
  styleUrls: ['error-message.component.scss']
})
export class ErrorMessageComponent {

  @Input() errorMessage: ErrorMessage | null = null;
  @Output() readonly close: EventEmitter<void> = new EventEmitter<void>();

  closeMessage(): void {
    this.close.emit();
  }

}
