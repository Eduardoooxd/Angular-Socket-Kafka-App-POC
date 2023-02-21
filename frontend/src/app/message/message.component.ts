import { Component, Input, OnInit } from '@angular/core';
import { WebSocketService } from '../web-socket.service';
import { Message } from './message';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css'],
})
export class MessageComponent implements OnInit {
  @Input() message!: Message;

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {}

  currentUserMessage(): boolean {
    return this.message.sender === this.webSocketService.userUUID;
  }
}
