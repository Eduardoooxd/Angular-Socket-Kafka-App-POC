# <a id="-simple-real-time-notification-system-"> Simple Real-time Notification System </a>

[![GitHub license](https://img.shields.io/badge/license-GNU-blue.svg)](./LICENSE)

The Real-time Notification System is a distributed messaging system that allows a producer to generate notifications and publish them to a Kafka topic, which can be consumed by a consumer application. The system also includes a front-end application built with Angular that displays the notifications in real-time using a WebSocket connection.

## Table of Contents

- [ Simple Real-time Notification System ](#-simple-real-time-notification-system-)
  - [Table of Contents](#table-of-contents)
  - [ Architecture Overview ](#-architecture-overview-)
  - [ Technologies ](#-technologies-)
    - [Backend](#backend)
    - [Frontend](#frontend)
    - [Devops](#devops)
  - [ Getting Started ](#-getting-started-)
  - [ License ](#-license-)
  - [ Conclusion ](#-conclusion-)

## <a id="-architecture-overview-"> Architecture Overview </a>

![Component Diagram - Level 2](assets/Component%20Diagram%20-%20Level%202.png)

This system has a distributed architecture consisting of four main components:

1. <b> Producer application:</b> This is a Quarkus-based Java application that generates notifications and publishes them to a Kafka topic named "message-channel".
2. <b> Consumer application:</b> This is a Quarkus-based Java application that consumes the notifications from the Kafka topic and communicates with the front-end application using a WebSocket.

3. <b> Front-end application:</b> This is an Angular-based application that displays the notifications in real-time using a WebSocket connection, it communicates with Producer using an REST API and with consumer by an websocket for real time communication.

4. <b> Kafka:</b> This is a distributed streaming platform that is used to publish and consume messages. Once a message is published to a Kafka topic, it can be consumed by any application that is subscribed to that topic.

The system is designed to demonstrate the power of asynchronous messaging and real-time communication using modern technologies. It provides a simple yet effective framework for building real-time applications that can be easily scaled and extended as needed. The user interface is fully customizable, allowing developers to tailor the experience to their specific needs.

## <a id="-technologies-"> Technologies </a>

The Real-time Notification System uses the following technologies:

### Backend

-   [Quarkus](https://quarkus.io/): A lightweight and fast Java framework for building microservices and serverless applications, know for its speed, efficiency, and low memory footprint, making it an ideal choice for microservices that need to scale quickly and efficiently. <br> Quarkus was chosen because it already provides support for Kafka, making it easy to integrate with the messaging system, besides that Quarkus also contains a WebSocket client, providing this way all the necessary requirements. <br> Either way, an nodejs application it would be also a good choice for the consumer application, mainly because of better performance for higher I/O operations, due the JavaScript nature.

-   [Kafka](https://kafka.apache.org/): A distributed streaming platform that is used to publish and consume messages. Once a message is published to a Kafka topic, it can be consumed by any application that is subscribed to that topic. Kafka was chosen for its ability to handle large volumes of data, making it an ideal choice for building real-time applications, rabitmq was also considered but it was discarded due the kafka vantages mentioned.

### Frontend

-   [Angular](https://angular.io/): A TypeScript-based open-source web application framework. Angular providecs ease of use, flexibility, and extensibility, making it an ideal choice for building modern web applications. <br> Angular was chosen because it already provides support for WebSockets, making it easy to integrate with the consumer application, besides that Angular also contains a HTTP client, providing this way all the necessary requirements. <br> Either way, all current FE frameworks for web development would also be a good choice for the front-end application, but they would require the installation of libraries for HTTP Requests (e.g. Axios) and Websockets (e.g. Socket.IO, SockJS, ws, etc...) .

### Devops

-   [Docker](https://www.docker.com/): A platform for building, shipping, and running applications in containers. Docker was chosen for its ability to simplify deployment and management of the system.

By using Quarkus for the backend, Angular for the frontend, and Kafka for the messaging system, the Real-time Notification System is designed to be fast, efficient, and scalable. The system can handle large volumes of data, while still providing real-time updates to the end-users. The use of Docker and Docker Compose makes it easy to deploy and manage the system, making it an ideal choice for organizations that need a reliable, real-time notification system.

## <a id="-getting-started-"> Getting Started </a>

To get started with the project, you will need to have the following tools installed:

-   Git CLI
-   Docker

Once you have installed the necessary tools, you can clone the repository to your local machine:

```bash
  git clone https://github.com/Eduardoooxd/Simple-Real-Time-Notification-System.git
```

After cloning the repository, you can use the provided Docker Compose file to run the system:

```bash
  docker-compose up
```

This command will start all the necessary components of the system, including the Kafka broker and the producer, consumer, and front-end applications. You can access the front-end application by navigating to http://localhost:80 in your web browser.

That's it! With just a few simple commands, you can run the Real-time Notification System in a Docker container on your local machine.

## <a id="-license-"> License </a>

This project is licensed under the GNU License - see the [LICENSE](./LICENSE) file for details.

## <a id="-conclusion-"> Conclusion </a>

This project demonstrates the power of asynchronous messaging and real-time communication using modern technologies such as Angular, Quarkus, and Kafka. It provides a simple yet effective framework for building real-time applications that can be easily scaled and extended as needed. The user interface is fully customizable, allowing developers to tailor the experience to their specific needs.
