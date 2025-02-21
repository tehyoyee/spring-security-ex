    //package com.taehyeong.backend.config;
    //
    //import jakarta.servlet.http.HttpServletRequest;
    //import org.springframework.http.server.ServerHttpRequest;
    //import org.springframework.http.server.ServerHttpResponse;
    //import org.springframework.http.server.ServletServerHttpRequest;
    //import org.springframework.stereotype.Component;
    //import org.springframework.web.socket.WebSocketHandler;
    //import org.springframework.web.socket.server.HandshakeInterceptor;
    //
    //import java.util.Map;
    //
    //@Component
    //public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    //    @Override
    //    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
    //                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    //        if (request instanceof ServletServerHttpRequest) {
    //            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
    //            String cookie = servletRequest.getHeader("Cookie");
    //            attributes.put("Cookie", cookie);
    //        }
    //        System.out.println("http handshake begin");
    //        return true;
    //    }
    //
    //    @Override
    //    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
    //                               WebSocketHandler wsHandler, Exception exception) {
    //        // 필요시 후처리
    //    }
    //}
