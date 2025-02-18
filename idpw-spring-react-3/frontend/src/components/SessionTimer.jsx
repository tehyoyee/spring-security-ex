import { useRef, useState, useEffect } from "react";

export const SessionTimer = () => {
    const [elapsedTime, setElapsedTime] = useState(0);
    const startTimeRef = useRef(null);
    const timerRef = useRef(null);
  
    useEffect(() => {
      // 타이머 시작 시점 기록
      startTimeRef.current = Date.now();
  
      // 타이머 함수: 현재 시간과 시작 시간의 차이를 계산
      const updateTimer = () => {
        const now = Date.now();
        const thisTime = (now - startTimeRef.current) / 1000;
  
        setElapsedTime(now - startTimeRef.current);
      };
  
      // 일정 주기(예: 100ms)마다 타이머 업데이트
      timerRef.current = setInterval(updateTimer, 1);
  
      // 컴포넌트 언마운트 시 타이머 정리
      return () => {
        clearInterval(timerRef.current);
      };
    }, []);
  
    return (
      <div>
        <h1>타이머</h1>
        <p>경과 시간: {(elapsedTime / 1000).toFixed(3)} 초</p>
      </div>
    );
  };