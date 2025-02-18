// // StompClientExample.js
// import React, { useEffect, useState } from 'react';
// import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

// function StompClientExample() {
//   const [client, setClient] = useState(null);
//   const [messages, setMessages] = useState([]);
//   const [connected, setConnected] = useState(false);

//   useEffect(() => {
//     // STOMP 클라이언트 생성
//     const stompClient = new Client({
//       // brokerURL을 사용하지 않고, SockJS를 사용합니다.
//       webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
//       reconnectDelay: 5000,
//       debug: (str) => {
//         console.log(str);
//       },
//       onConnect: () => {
//         console.log('STOMP 연결 성공');
//         setConnected(true);

//         // 서버가 /topic/messages로 방송하는 메시지를 구독
//         stompClient.subscribe('/topic/messages', (message) => {
//           if (message.body) {
//             console.log("Received message: ", message.body);
//             setMessages(prev => [...prev, message.body]);
//           }
//         });
//       },
//       onStompError: (frame) => {
//         console.error('Broker error: ' + frame.headers['message']);
//         console.error('Details: ' + frame.body);
//       }
//     });

//     stompClient.activate();
//     setClient(stompClient);

//     return () => {
//       stompClient.deactivate();
//     };
//   }, []);

//   const sendMessage = () => {
//     if (client && connected) {
//       // 클라이언트가 /app/chat 경로로 메시지를 보냄 (서버의 @MessageMapping("/chat")과 매핑)
//       client.publish({
//         destination: '/app/chat',
//         body: 'Hello from React!'
//       });
//     }
//   };

//   return (
//     <div style={{ padding: '20px' }}>
//       <h1>STOMP Client Example</h1>
//       <button onClick={sendMessage} disabled={!connected}>
//         Send Message
//       </button>
//       <h2>Received Messages:</h2>
//       <ul>
//         {messages.map((msg, idx) => <li key={idx}>{msg}</li>)}
//       </ul>
//     </div>
//   );
// }

// export default StompClientExample;
