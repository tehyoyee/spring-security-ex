// // store.js
// import { createStore, applyMiddleware } from 'redux';
// import thunk from 'redux-thunk';
// import authReducer from './reducer';

// const store = createStore(authReducer);

// export default store;


// import { configureStore } from '@reduxjs/toolkit'

// export default configureStore({
//   reducer: {}
// })  

import { configureStore } from '@reduxjs/toolkit'
import counterReducer from './actions'
import authReducer from './reducer'

export default configureStore({
  reducer: {
    auth: authReducer
  }
})