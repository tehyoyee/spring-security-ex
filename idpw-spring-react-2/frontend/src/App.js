import logo from './logo.svg';
import './App.css';
import "@radix-ui/themes/styles.css";
import { Button, Flex, Text, Theme } from '@radix-ui/themes';
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom';
import Home from './pages/Home';
import NavBar from './components/Navbar';
import SignUp from './pages/SignUp';
import Login from './pages/Login';
import { Provider } from 'react-redux';
import store from './redux/store';

function App() {
  return (
    // <div className="App">
    <Provider store={store}>
      <BrowserRouter>
        <NavBar/>
          <Routes>
            <Route path="/" element={ <Home /> } />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
          </Routes>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
