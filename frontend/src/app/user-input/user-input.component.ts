import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Message } from '../message/message';
import { WebSocketService } from '../web-socket.service';
import { ProducerService } from './../producer.service';

@Component({
  selector: 'app-user-input',
  templateUrl: './user-input.component.html',
  styleUrls: ['./user-input.component.css'],
})
export class UserInputComponent implements OnInit {
  userInputForm = new FormGroup({
    userQuestion: new FormControl(''),
  });

  constructor(
    private webSocketService: WebSocketService,
    private producerService: ProducerService
  ) {}

  ngOnInit(): void {}

  sendRESTMessage(): void {
    const message = this.createMessageToSend();
    this.producerService.sendRestMessage(message).subscribe({
      error: (error) => console.error(error),
    });

    this.resetInputField();
  }

  sendWebsocketMessage(): void {
    const message = this.createMessageToSend();
    this.webSocketService.sendMessage(message);
    this.resetInputField();
  }

  createMessageToSend(): Message {
    return {
      sender: this.webSocketService.userUUID,
      content: this.userQuestionValue,
      timestamp: new Date().toUTCString(),
    };
  }

  resetInputField(): void {
    this.userInputForm.reset();
  }

  get userQuestionValue(): string {
    return this.userInputForm.get('userQuestion')?.value;
  }
}
