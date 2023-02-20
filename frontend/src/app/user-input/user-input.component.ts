import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Message } from '../message/message';
import { WebSocketService } from '../web-socket.service';

@Component({
  selector: 'app-user-input',
  templateUrl: './user-input.component.html',
  styleUrls: ['./user-input.component.css'],
})
export class UserInputComponent implements OnInit {
  userInputForm = new FormGroup({
    userQuestion: new FormControl(''),
  });

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {}

  sendRESTMessage(): void {
    console.log(this.userQuestionValue, 'REST');
    this.resetInputField();
  }

  sendWebsocketMessage(): void {
    const message: Message = {
      sender: this.webSocketService.userUUID,
      content: this.userQuestionValue,
      timestamp: new Date().toUTCString(),
    };
    this.webSocketService.sendMessage(message);
    this.resetInputField();
  }

  resetInputField(): void {
    this.userInputForm.reset();
  }

  get userQuestionValue(): string {
    return this.userInputForm.get('userQuestion')?.value;
  }
}
