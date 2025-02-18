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
import store from './redux/store';
import StompClientExample from './components/SockClientExample';
import SessionChecker from './components/SessionChecker';

function App() {
  // const dispatch = useDispatch();
  // const auth = useSelector((state) => state.auth);

  return (
    // <div className="App">
    <Provider store={store}>
    <div>
      <div>
        {/* 로그인상태 : {auth.isLoggedIn} */}

      </div>
      <SessionChecker />
      <BrowserRouter>
        <NavBar/>
          <Routes>
            <Route path="/" element={ <Home /> } />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
          </Routes>
      </BrowserRouter>


    </div>
    </Provider>
  );
}

export default App;
