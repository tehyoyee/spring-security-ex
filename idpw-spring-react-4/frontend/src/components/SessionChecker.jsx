import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

function SessionChecker() {
  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const navigate = useNavigate();
  const { isLoggedIn, user, login, logout } = useAuth();

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (connected && client) {
        client.publish({
          destination: '/app/session-checker',
          body: "SESSION_CHECKER"
        });
      } else {
        console.log('clearInterval');
        clearInterval(intervalId);
      }
    }, 5000);
    return () => clearInterval(intervalId);
  }, [client, connected]);
  

  useEffect(() => {
    if (!isLoggedIn) {
      if (connected) {
        console.log('디스커넥트')
        disconnect();
      }
      return;
    }
    // STOMP 클라이언트 생성
    const stompClient = new Client({
      // brokerURL, SockJS 2개 중 SockJS 
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      // webSocketFactory: () => new SockJS('http://localhost:8080/ws', null, { xhrWithCredentials: true }),
      reconnectDelay: 5000,
      debug: (str) => {
        console.log('debug', str);
      },
      onConnect: () => {
        stompClient.subscribe('/user/queue/reply', (message) => {
          console.log("PING BODY", message.body);
          if (message.body === 'ALIVE') {
            // login()
          }
          if (isLoggedIn) {
            if (message.body === 'DUPLICATE') {
              logout()
              navigate('/login');
              alert("세션이 만료되었습니다.");
              return;
            }
            if (message.body === 'EXPIRED') {
              logout()
              navigate('/login');
              alert("세션이 만료되었습니다.");
              return;
            }
          } 
          if (message.body) {
            console.log("받은 메시지: ", message.body);
          }
        });
        console.log('Client active:', stompClient.active); // 이 시점에서는 true여야 함
        setConnected(true);


      },
      onDisconnect: () => {
        setConnected(false)
        console.log("disconnected");
      },
      onStompError: (frame) => {
        console.error('Broker error: ' + frame.headers['message']);
        console.error('Details: ' + frame.body);
      },
      onWebSocketClose: (evt) => {
        console.warn("웹소켓 연결 종료", evt);
        setConnected(false);
      }
      
    });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      stompClient.deactivate();
    };
  }, [isLoggedIn]);

  const sendMessage = () => {
    if (client && connected) {
      // 클라이언트가 /app/chat 경로로 메시지를 보냄 (서버의 @MessageMapping("/chat")과 매핑)
      client.publish({
        destination: '/app/chat',
        body: 'Hello from React!'
      });
    }
  };

  const disconnect = () => {
    setConnected(false)
    console.log('set disconnect')
    if (client && connected) {
      client.deactivate();
    }
  };
  const reconnect = () => {
    setConnected(true)
    console.log("재연결")
    if (client && !connected) {
      client.activate();
    }
  };
  
  return (
    <div>
      { isLoggedIn ? "로그인상태" : "로그아웃상태"}
      <button onClick={disconnect} disabled={!connected}>
        연결 끊기
      </button>
      <button onClick={reconnect} disabled={connected}>
        재연결
      </button>
      <button onClick={() => {
        axios
        .get('http://localhost:8080/members/logout', {
          withCredentials: true
        })
        .then((response) => {
          logout()
        })
        .catch((error) => {
          console.error('API 호출 중 오류 발생:', error);
        });
      }}>
        로그아웃
      </button>
    </div>
  );
}

export default SessionChecker;
