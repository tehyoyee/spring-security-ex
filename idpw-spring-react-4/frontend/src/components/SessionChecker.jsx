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
  const { isLoggedIn, stompChannel, login, logout, setStompChannel, initialized } = useAuth();
  const asdf = useAuth();

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (initialized) {

      }
      if (connected && client && stompChannel && stompChannel.length==32) {
        client.publish({
          destination: '/app/chat',
          body: `${ stompChannel }`
        });
        // console.log('sessionChecker : stompChannel ', stompChannel, 'isLoggendIn ', isLoggedIn);
      } else {
        // console.log('sessionChecker : clearInterval');
        clearInterval(intervalId);
      }
    }, 500);
    return () => clearInterval(intervalId);
  }, [client, connected]);
  

  useEffect(() => {
    // console.log('웹소켓 연결 시도')
    if (!initialized) {
      // console.log('웹소켓 연결 거절') 
      return;
    }
    // console.log('웹소켓 연결 승인')
    const stompClient = new Client({
      // brokerURL, SockJS 2개 중 SockJS 
      // webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      webSocketFactory: () => new SockJS('http://localhost:8080/ws', null, { xhrWithCredentials: true }),
      reconnectDelay: 5000,
      debug: (str) => {
        // console.log('debug', str);
      },
      onConnect: () => {
        console.warn('웹소켓 연결 완료');
        stompClient.subscribe(`/topic/${ stompChannel }`, (message) => {
          // console.log(`STOMP CHANNEL : ${ stompChannel } received message ${ message.body }`)
          switch (message.body) {
            case 'ALIVE':
              // console.log('STOMP CHANNEL : ALIVE')
              break;
            case 'EXPIRED':
              // console.log('STOMP CHANNEL : EXPIRED')
              logout()
              setStompChannel("");
              localStorage.removeItem('stompChannel')
              navigate('/login');
              alert("세션만료로 로그아웃되었습니다.");
              break;
            case 'DUPLICATE':
              // console.log('STOMP CHANNEL : DUPLICATE')
              logout()
              setStompChannel("");
              localStorage.removeItem('stompChannel')
              navigate('/login');
              alert("중복로그인으로 로그아웃 처리합니다.");
              break;
            case 'LOGOUT':
              // console.log('STOMP CHANNEL : LOGOUT')
              logout()
              setStompChannel("");
              localStorage.removeItem('stompChannel')
              navigate('/login');
              alert("로그아웃 되었습니다.");
              break;
            case 'KICKED':
              // console.log('STOMP CHANNEL : KICKED')
              logout()
              setStompChannel("");
              localStorage.removeItem('stompChannel')
              alert("관리자에 의해 로그아웃되었습니다.");
              navigate('/login');
              break;
            default:
              // console.log('STOMP CHANNEL : UNKNOWN')
              logout()
              setStompChannel("");
              localStorage.removeItem('stompChannel');
              navigate('/login');
              alert("알 수 없는 이유로 로그아웃되었습니다.");
          }
        });
        stompClient.subscribe('/user/queue/reply', (message) => {
          if (message.body === 'ALIVE') {
            // login()
          }
          if (isLoggedIn) {
            if (message.body === 'DUPLICATE') {
              logout()
              navigate('/login');
              alert("중복로그인으로 로그아웃 처리합니다.");
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
        setConnected(true);
        client.publish({
          destination: '/app/chat',
          body: `${ stompChannel }`
        });

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
  }, [isLoggedIn, initialized]);

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
      { isLoggedIn === true ? "로그인 상태" : "로그아웃 상태"}
      <button onClick={disconnect} disabled={!connected}>
        연결 끊기
      </button>
      <button onClick={reconnect} disabled={connected}>
        재연결
      </button>
      <button onClick = { async () => {
        const res = await fetch("http://localhost:8080/members/logouts", {
          method: "POST",
          credentials: 'include',
          headers: {
            'ContentType': 'application/json'
          }
        });
        if (res.ok) {
          const data = await res.json();
          if (data.success) {
            alert('로그아웃되었습니다')
            logout();
            setStompChannel('');
            localStorage.removeItem('stompChannel');
            navigate('/login');

          }
        }
    }}>
        로그아웃
      </button>
    </div>
  );
}

export default SessionChecker;
