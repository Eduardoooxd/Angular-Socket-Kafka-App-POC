import { Injectable, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs';
import { WebSocketSubject, webSocket } from 'rxjs/webSocket';
import { v4 as uuidv4 } from 'uuid';
import { environment } from './../environments/environment';
import { Message } from './message/message';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService implements OnDestroy {
  private readonly SOCKET_SERVER_URL = environment.webSocketServer;
  private readonly CONNECTION_UUID = uuidv4();

  private socket$!: WebSocketSubject<Message>;

  constructor() {
    this.createWebSocket();
  }

  ngOnDestroy() {
    this.closeConnection();
  }

  public connect(): Observable<Message> {
    if (!this.socket$) {
      this.createWebSocket();
    }
    return this.socket$.asObservable();
  }

  public sendMessage(message: Message): void {
    this.socket$?.next(message);
  }

  get userUUID(): string {
    return this.CONNECTION_UUID;
  }

  private createWebSocket() {
    this.socket$ = webSocket<Message>(
      `${this.SOCKET_SERVER_URL}${this.CONNECTION_UUID}`
    );
    const ackMessage: Message = {
      sender: this.CONNECTION_UUID,
      content: 'Connecting to Consumer',
      timestamp: new Date().toISOString(),
    };

    this.socket$.next(ackMessage);

    this.socket$.subscribe({
      error: (e) => console.error(e),
      next: (message) => console.log(message),
      complete: () => console.log('complete'),
    });
  }

  private closeConnection(): void {
    if (this.socket$) {
      this.socket$?.complete();
    }
  }
}
