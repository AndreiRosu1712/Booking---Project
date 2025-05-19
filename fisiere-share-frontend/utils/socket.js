// src/utils/socket.js
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;

export const connectSocket = (onMessage) => {
  const socket = new SockJS('http://localhost:8080/ws');
  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('🔌 Conectat la WebSocket');
    },
    onStompError: (frame) => {
      console.error('Eroare WebSocket:', frame.headers['message']);
    },
  });

  stompClient.onConnect = () => {
    console.log('✅ STOMP conectat');

    // Subscribe example – înlocuiește cu valori reale
    // stompClient.subscribe('/topic/conversations/IDHOTEL/IDCLIENT', message => {
    //   const msg = JSON.parse(message.body);
    //   onMessage(msg);
    // });
  };

  stompClient.activate();
};

export const sendMessage = (messageDto) => {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(messageDto),
    });
  } else {
    console.warn('⚠️ STOMP nu e conectat încă!');
  }
};
