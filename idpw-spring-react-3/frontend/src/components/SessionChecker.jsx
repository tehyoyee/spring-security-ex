// StompClientExample.js
import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useDispatch, useSelector } from 'react-redux';
import { login, logout } from '../redux/authSlice';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

function SessionChecker() {
  const [client, setClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const dispatch = useDispatch();
  const auth = useSelector((state) => state.auth);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (connected && client) {
        // client.publish({
        //   destination: '/app/chat',
        //   body: "SESSION_CHECKER"
        // });
        client.publish({
          destination: '/app/session-checker',
          body: "SESSION_CHECKER"
        });
        
      } else {
        console.log('clearInterval');
        clearInterval(intervalId);
      }
    }, 5000);
  
    // 컴포넌트 언마운트 시 interval 클리어
    return () => clearInterval(intervalId);
  }, [connected]);
  

  useEffect(() => {
    // STOMP 클라이언트 생성
    const stompClient = new Client({
      // brokerURL을 사용하지 않고, SockJS를 사용합니다.
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      // webSocketFactory: () => new SockJS('http://localhost:8080/ws', null, { xhrWithCredentials: true }),
      reconnectDelay: 5000,
      debug: (str) => {
        console.log('debug', str);
      },
      onConnect: () => {
        setConnected(true);
        const sessionId = Cookies.get('JSESSIONID');
        console.log('jsessionid ', auth)
        stompClient.subscribe('/user/queue/reply', (message) => {
          console.log('auth', auth)
          if (auth.isLoggedIn && !message.body === 'EXPIRED') {
            console.log("wbfewuifbewufiewbfuewibfui")
            dispatch(logout());
            alert("세션이 만료되었습니다.");
          }
          if (message.body) {
            console.log("받은 메시지: ", message.body);
          }
        });
        console.log('Client active:', stompClient.active); // 이 시점에서는 true여야 함

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
  }, []);

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
      { auth.isLoggedIn ? "로그인상태" : "로그아웃상태"}
      <button onClick={disconnect} disabled={!connected}>
        연결 끊기
      </button>
      <button onClick={reconnect} disabled={connected}>
        재연결
      </button>
    </div>
  );
}

export default SessionChecker;
