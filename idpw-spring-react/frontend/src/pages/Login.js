// src/pages/Login.js
import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../redux/actionTypes";
import store from "../redux/store";

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  width: 300px;
  padding: 2rem;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
`;

const Input = styled.input`
  padding: 0.8rem;
  margin-bottom: 1rem;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;

  &:focus {
    outline: none;
    border-color: #007bff;
    box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
  }
`;

const Button = styled.button`
  padding: 0.8rem;
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

const ErrorMessage = styled.p`
  color: red;
  font-size: 0.9rem;
  margin-top: 0.5rem;
`;
// export const login = (username, password) => {
//     return async (dispatch) => {
//         dispatch({ type: LOGIN_REQUEST }); // 로그인 요청 시작

//         try {
//             // POST 요청을 통해 로그인 시도
//             const response = await axios.post('http://localhost:8080/login', { 
//                 username, 
//                 password 
//             }, { 
//                 withCredentials: true // 쿠키와 자격 증명을 함께 전송
//             });

//             // 로그인 성공 시 액션 디스패치
//             dispatch({ type: LOGIN_SUCCESS, payload: response.data });
//         } catch (error) {
//             // 로그인 실패 시 액션 디스패치
//             dispatch({ type: LOGIN_FAILURE, payload: error.response?.data || 'Login failed' });
//         }
//     };

const Login = () => {


    // dispatch({ type: LOGIN_REQUEST });
    // try {
    //     const response = await axios.post('http://localhost:8080/login', { username, password }, { withCredentials: true });
    //     dispatch({ type: LOGIN_SUCCESS, payload: response.data });
    // } catch (error) {
    //     dispatch({ type: LOGIN_FAILURE, payload: error.response.data });
    // }
  const [formData, setFormData] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();
  
  const count = useSelector(state => state.auth.value)
  const dispatch = useDispatch()
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // dispatch(request);
    dispatch({ type: LOGIN_REQUEST }); // 로그인 요청 시작
    console.log("store STATE " + store.getState().auth.login);
    try {
      setError(""); // Clear previous errors
        console.log("요청")
    //   const response = await axios({
    //     url: 'http://localhost:8080/login',
    //     method: 'POST',
    //     data: body,
    //     withCredentials: true,
    //   });
    console.log(formData)
      const response = await axios({
        url: 'http://localhost:8080/login',
        method: 'POST',
        data: JSON.stringify(formData),
        headers: {
            'Content-Type': 'application/json', // Content-Type 헤더 설정
        },
        withCredentials: true,
      });
      console.log("요청끝")
      dispatch({ type: LOGIN_SUCCESS }); // 로그인 요청 시작
      console.log("store STATE " + store.getState().auth.login);
    } catch (err) {
        if (store.getState().auth.login === 'LOGIN_SUCCESS') {
            // dispatch({ type: LOGIN_FAILURE }); // 로그인 요청 시작
            alert('세션이 만료되었습니다. 다시 로그인해주세요.');
        }
            dispatch({ type: LOGIN_FAILURE }); // 로그인 요청 시작
            alert('로그인이 필요합니다.');
            console.log("store STATE " + store.getState().auth.login);
      setError("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
    }
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Input
          type="text"
          name="username"
          placeholder="아이디"
          value={formData.username}
          onChange={handleChange}
          required
        />
        <Input
          type="password"
          name="password"
          placeholder="비밀번호"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <Button type="submit">로그인</Button>
        {error && <ErrorMessage>{error}</ErrorMessage>}
      </Form>
    </Container>
  );
};

export default Login;
