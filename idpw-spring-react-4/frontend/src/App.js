import logo from './logo.svg';
import './App.css';
import "@radix-ui/themes/styles.css";
import { Button, Flex, Text, Theme } from '@radix-ui/themes';
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom';
import Home from './pages/Home';
import NavBar from './components/Navbar';
import SignUp from './pages/SignUp';
import Login from './pages/Login';
import { Provider, useDispatch, useSelector } from 'react-redux';
import store, { persistor } from './redux/store';
import StompClientExample from './components/SockClientExample';
import SessionChecker from './components/SessionChecker';
import { PersistGate } from 'redux-persist/integration/react';
import { useEffect } from 'react';
import { AuthProvider } from './context/AuthContext';

function App() {
  // const dispatch = useDispatch();
  // const auth = useSelector((state) => state.auth);
  // useEffect(() => {
  //   if (auth.isLoggedIn) {
  //     // 로그인 성공 시, 예를 들어 auth.token이나 auth.id 등을 사용하여 재연결
  //     // SessionChecker();
  //   }
  //   // 로그아웃 시 stompClient를 종료하고 싶다면 추가 처리 가능
  // }, [auth.isLoggedIn]);
  return (
    // <div className="App">
    // <Provider store={store}>
    //       <PersistGate loading={null} persistor={persistor}>
    <AuthProvider>
      <div>
        <div>
          {/* 로그인상태 : {auth.isLoggedIn} */}

        </div>
        <BrowserRouter>
        <SessionChecker />
          <NavBar/>
            <Routes>
              <Route path="/" element={ <Home /> } />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/login" element={<Login />} />
            </Routes>
        </BrowserRouter>


      </div>

    </AuthProvider>
    // </PersistGate>

    // </Provider>
  );
}

export default App;
