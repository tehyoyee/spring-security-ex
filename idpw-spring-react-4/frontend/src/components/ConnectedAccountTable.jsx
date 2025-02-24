import React, { useRef, useEffect, useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

// ========== styled-components 예시 ==========
const Table = styled.table`
  width: 80%;
  border-collapse: collapse;
  margin: 20px auto;
  font-family: Arial, sans-serif;
  /* display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; */
`;

const TableHead = styled.thead`
  background-color: #f2f2f2;
`;

const MarginHorizon = styled.div`
  margin-top: 50px;
  
`

const TableTitle = styled.div`
  font-size: 30px;
  text-align: center;
  font-weight: 1000;
`

const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #fafafa;
  }
`;

const TableHeader = styled.th`
  padding: 10px;
  border: 1px solid #ddd;
`;

const TableData = styled.td`
  padding: 10px;
  border: 1px solid #ddd;
  text-align: center;
`;

// ========== 메인 컴포넌트 ==========
function ConnectedAccountTable() {
  // 서버에서 받아온 계정 목록 상태
  // const [messages, setMessages] = useState([]);
  const [accounts, setAccounts] = useState([]);
  const [sessionHistory, setSessionHistory] = useState([]);
  const [sessionActive, setSessionActive] = useState([]);
  const navigate = useNavigate();
  const { isLoggedIn, user, login, logout } = useAuth();  
  const hasRedirected = useRef(false);

// 클린업: 컴포넌트 언마운트 시 STOMP 클라이언트 비활성화
  console.log('user', user)
  // 마운트 시점에 API 호출
  
      const invalidateSession = async (sessionId) => {
        
        axios
          .delete(`http://localhost:8080/sessions/${ sessionId }`, {
            withCredentials: true
          })
          .then((response) => {
            if (response.status === 200) {
              console.log(response.data.message);
              alert(response.data.message);
            }
          })
          .catch((error) => {
            if (error.status === 400) {
              alert(error.response.data.message)
            }
            console.log('error', error)
            // navigate('/login')
          });
  
      }
  useEffect(() => {
    let intervalId;

    const fetchData = async () => {
      axios
        .get('http://localhost:8080/sessions', {
          withCredentials: true
        })
        .then((response) => {
          setAccounts(response.data.data); // 데이터 구조에 맞춰 세팅
        })
        .catch((error) => {
          if (hasRedirected) {
            return;
          }
          hasRedirected.current = true;
          navigate('/login')
          if (isLoggedIn) {
            alert(error.response.data.message);
          } else {
            alert('로그인 해주세요.')
          }
          // console.error('API 호출 중 오류 발생:', error);
        });

      axios
      .get('http://localhost:8080/sessions/inactive', {
        withCredentials: true
      })
      .then((response) => {
        setSessionHistory(response.data.data); // 데이터 구조에 맞춰 세팅
      })
      .catch((error) => {
        // console.error('API 호출 중 오류 발생:', error);
      });
      axios
      .get('http://localhost:8080/sessions/active', {
        withCredentials: true
      })
      .then((response) => {
        setSessionActive(response.data.data); // 데이터 구조에 맞춰 세팅
      })
      .catch((error) => {
        // console.error('API 호출 중 오류 발생:', error);
      });
    };

    fetchData();
    intervalId = setInterval(fetchData, 1000); // 1000ms = 1초

    // 클린업 함수: 컴포넌트 언마운트 시 인터벌 해제
    return () => clearInterval(intervalId);
  }, []);

  return (
    <>
    <MarginHorizon/>
          <TableTitle> 스프링 서버가 관리하는 세션 </TableTitle>
      <Table>
        <TableHead>
          <TableRow>
            <TableHeader>ID</TableHeader>
            <TableHeader>Username</TableHeader>
            <TableHeader>Session ID</TableHeader>
            <TableHeader>최대 시간</TableHeader>
            <TableHeader>남은 시간</TableHeader>
            <TableHeader>역할</TableHeader>
            <TableHeader>권한</TableHeader>
            <TableHeader>세션 만료</TableHeader>
            
          </TableRow>
        </TableHead>

        <tbody>
          { accounts[0] && accounts.map((acc, idx) => (
            <TableRow key={acc.idx}>
              <TableData>{acc.id}</TableData>
              <TableData>{acc.username}</TableData>
              <TableData>{acc.sessionId}</TableData>
              <TableData>{acc.expireTime}</TableData>
              <TableData>{acc.remainingTime}</TableData>
              <TableData>{acc.roleName}</TableData>
              <TableData>{acc.authorities}</TableData>
              <TableData>{acc.sessionExpired ? "만료":"유지"} </TableData>
            </TableRow>
          ))}
        </tbody>
      </Table>
      <MarginHorizon/>

      <TableTitle> Active Sessions </TableTitle>

      <Table>
        <TableHead>
          <TableRow>
            <TableHeader>ID</TableHeader>
            <TableHeader>Username</TableHeader>
            <TableHeader>Session ID</TableHeader>
            <TableHeader>Stomp Channel</TableHeader>
            <TableHeader>상태</TableHeader>
            <TableHeader>시작 시간</TableHeader>
            <TableHeader>세션 종료</TableHeader>
          </TableRow>
        </TableHead>

        <tbody>
          { sessionActive[0] && sessionActive.map((session, idx) => (
            <TableRow key={ idx }>
              <TableData>{session.userId}</TableData>
              <TableData>{session.username}</TableData>
              <TableData>{session.sessionId}</TableData>
              <TableData>{session.stompChannel}</TableData>
              <TableData>{session.status}</TableData>
              <TableData>{session.startTime}</TableData>
              <TableData><button onClick={ () => invalidateSession(session.sessionId) }>세션 종료</button></TableData>
            </TableRow>
          ))}
        </tbody>
      </Table>
    <MarginHorizon/>
      

      <TableTitle> Invalid Sessions </TableTitle>

      <Table>
        
        <TableHead>
          <TableRow>
            <TableHeader>ID</TableHeader>
            <TableHeader>Username</TableHeader>
            <TableHeader>Session ID</TableHeader>
            <TableHeader>Stomp Channel</TableHeader>
            <TableHeader>상태</TableHeader>
            <TableHeader>시작 시간</TableHeader>
            <TableHeader>끝난 시간</TableHeader>

            
          </TableRow>
        </TableHead>

        <tbody>
          { sessionHistory[0] && sessionHistory.map((session, idx) => (
            <TableRow key={ idx }>
              <TableData>{session.userId}</TableData>
              <TableData>{session.username}</TableData>
              <TableData>{session.sessionId}</TableData>
              <TableData>{session.stompChannel}</TableData>
              <TableData>{session.status}</TableData>
              <TableData>{session.startTime}</TableData>
              <TableData>{session.endTime}</TableData>
            </TableRow>
          ))}
        </tbody>
      </Table>
    </>
  );
}

export default ConnectedAccountTable;
