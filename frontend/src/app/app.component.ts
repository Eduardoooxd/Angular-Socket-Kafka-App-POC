import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { Message } from './message/message';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  messages?: Message[] = new Array<Message>();
  message$?: Subscription;
  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    this.message$ = this.webSocketService.connect().subscribe({
      next: (message) => {
        this.messages?.push(message);
      },
      error: (err) => console.error(err),
      complete: () => console.log('complete'),
    });
  }

  ngOnDestroy() {
    this.message$?.unsubscribe();
  }

  currentUserMessage(message: Message): boolean {
    return message.sender === this.webSocketService.userUUID;
  }
}
