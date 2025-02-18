import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';


// ========== styled-components 예시 ==========
const Table = styled.table`
width: 80%;
border-collapse: collapse;
margin: 20px auto;
font-family: Arial, sans-serif;
`;

const TableHead = styled.thead`
background-color: #f2f2f2;
`;

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
// 서버에서 받아온 계정 목록 상태
function SessionRemaining() {
    const [accounts, setAccounts] = useState([]);

    // 마운트 시점에 API 호출
    // useEffect(() => {
    //     axios
    //     .get('http://localhost:8080/member/session-remaining', {
    //         withCredentials: true
    //     })
    //     .then((response) => {
    //         console.log(response.data.data)
    //         setAccounts(response.data.data); // 데이터 구조에 맞춰 세팅
    //     })
    //     .catch((error) => {
    //         console.error('API 호출 중 오류 발생:', error);
    //     });
    // }, []);
    return (
        <Table>
          <TableHead>
            <TableRow>
              <TableHeader>메시지</TableHeader>
              <TableHeader>최대 시간</TableHeader>
              <TableHeader>남은 시간</TableHeader>
            </TableRow>
          </TableHead>
    
          <tbody>

              <TableRow>
                <TableData>{accounts.message}</TableData>
                <TableData>{accounts.maxInactiveSec}</TableData>
                <TableData>{accounts.remainingSec}</TableData>
              </TableRow>
            
          </tbody>
        </Table>
      );
    
}
export default SessionRemaining;