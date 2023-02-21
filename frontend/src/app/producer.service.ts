import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../environments/environment';
import { Message } from './message/message';

@Injectable({
  providedIn: 'root',
})
export class ProducerService {
  private readonly PRODUCER_SERVER = environment.producerServer;

  constructor(private http: HttpClient) {}

  sendRestMessage(message: Message) {
    return this.http.post<Message>(this.PRODUCER_SERVER, message);
  }
}
