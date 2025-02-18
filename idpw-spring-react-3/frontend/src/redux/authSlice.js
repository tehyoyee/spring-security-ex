// authSlice.js
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isLoggedIn: false,
  id: 0, // 로그인한 사용자의 정보(예: id, 이름 등)
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login(state, id) {
      console.log('dispath login')
      state.isLoggedIn = true;
      state.id = id.payload;
    },
    logout(state) {
      console.log('dispath logout')
      state.isLoggedIn = false;
      state.id = 0;
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
