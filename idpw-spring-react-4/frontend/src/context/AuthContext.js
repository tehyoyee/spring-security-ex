import React, { createContext, useState, useContext, useEffect } from 'react';

// AuthContext 생성
const AuthContext = createContext();

// AuthProvider: 애플리케이션 전체에서 로그인 상태를 공유하도록 하는 Provider 컴포넌트
export const AuthProvider = ({ children }) => {
  // 로그인 상태와 사용자 정보를 관리하는 상태값
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [id, setId] = useState(0);

  useEffect(() => {
    const savedLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    if (savedLoggedIn === true) {
        setIsLoggedIn(savedLoggedIn);
        setId(parseInt(localStorage.getItem('id'), 10));
    // }
    }
  }, []);
  // 로그인 함수 (예: API 호출 후 사용자 정보 저장)
  const login = (id) => {
    setIsLoggedIn(true);
    setId(id);
    localStorage.setItem('id', id);
    localStorage.setItem('isLoggedIn', true); // 로그인 상태 저장
  };

  // 로그아웃 함수
  const logout = () => {
    setIsLoggedIn(false);
    setId(0)
    localStorage.removeItem('id');
    localStorage.removeItem('isLoggedIn'); // 저장된 로그인 상태 제거
  };

  const value = {
    isLoggedIn,
    login,
    logout,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

// AuthContext를 쉽게 사용하기 위한 커스텀 훅
export const useAuth = () => {
  return useContext(AuthContext);
};
