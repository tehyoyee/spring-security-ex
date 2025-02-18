//package com.taehyeong.backend.config;
//
//import com.taehyeong.backend.dto.request.UserRegisterReqDTO;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//@RequiredArgsConstructor
//@Component
//public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
//
//    private final HttpSession httpSession;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        // supportsParameter() 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
//        // @LoginUser 어노테이션이 붙어 있고 파라미터 클래스 타입이 SessionUser.class 인 경우 true 반환
////        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(User.class) != null;
//        boolean isUserClass = UserRegisterReqDTO.class.equals(parameter.getParameterType());
//
//        return isLoginUserAnnotation && isUserClass;
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        // resolve() 파라미터에 전달할 객체를 생성, 여기서는 세션에서 객체를 가져온다
//        return httpSession.getAttribute("user");
//    }
//}
