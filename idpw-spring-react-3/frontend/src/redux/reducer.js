// // reducer.js
// import { LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT } from './actionTypes';

// const initialState = {
//     loading: false,
// };

// const authReducer = (state = initialState, action) => {
//     switch (action.type) {
//         case LOGIN_REQUEST:
//             return { ...state, login: "ongoing", error: null };
//         case LOGIN_SUCCESS:
//             return { ...state, login: true, user: action.payload };
//         case LOGIN_FAILURE:
//             return { ...state, login: false, error: action.payload };
//         case LOGOUT:
//             return { ...state, user: null };
//         default:
//             return state;
//     }
// };

// export default authReducer;
