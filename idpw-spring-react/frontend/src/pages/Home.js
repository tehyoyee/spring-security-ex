// src/pages/Home.js
import axios from "axios";
import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import store from "../redux/store";
import { LOGIN_FAILURE, LOGIN_SUCCESS } from "../redux/actionTypes";

const HomeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f5f5;
  font-family: Arial, sans-serif;
`;

const Title = styled.h1`
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 1rem;
`;

const Subtitle = styled.p`
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 2rem;
`;

const Button = styled.button`
  padding: 10px 20px;
  font-size: 1rem;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: #0056b3;
  }
`;

const Home = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch()
  const handleClick = async () => {
    console.log("현재 로그인 상태 = ", store.getState().auth.login)
      try {
        const response = await axios.get("http://localhost:8080/test", {
            withCredentials: true
        });
        if (response.status === 200) {
            alert("test ok.");
            navigate("/"); // Redirect to home
        } else {
            alert(response.message);
        }
    } catch (e) {
      if (store.getState().auth.login === true) {
        alert("세션이 만료되었습니다.");
      } else {
        alert('로그인 해주세요.')  
      }
      dispatch({ type: LOGIN_FAILURE }); // 로그인 요청 시작
    }
  };

  return (
    <HomeContainer>
      <Title>Welcome to the Home Page</Title>
      <Subtitle>This is a simple example of a styled-component.</Subtitle>
      <Button onClick={handleClick}>Get Started</Button>
    </HomeContainer>
  );
};

export default Home;
