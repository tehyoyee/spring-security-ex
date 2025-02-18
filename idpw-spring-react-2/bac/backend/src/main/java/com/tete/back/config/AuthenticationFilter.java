//package com.taehyeong.backend.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    @Value("${jwt.signing.key}")
//    private String signingKey;
//
//    @Autowired
//    private TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        logger.info("JWT Filter's authenticating {} {}", request.getMethod(), request.getRequestURI());
//        String jwt = request.getHeader("Authorization");
//        if (jwt.isEmpty()) {
//            throw new CustomException(StatusCode.UNAUTHORIZED, "쿠키 값이 없습니다.");
//        }
//        tokenProvider.authenticate(TokenType.ACCESS, jwt, response);
//        logger.info("[{}] [{}] authenticated", request.getMethod(), request.getRequestURI() );
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
////        RequestMatcher requestMatcher = new RequestMatcher() {
////            @Override
////            public boolean matches(HttpServletRequest request) {
////                return false;
////            }
////        };
//        return
//                request.getServletPath().equals("/") ||
//                        request.getRequestURI().equals("/refresh") ||
//                        request.getServletPath().equals("/oauth2/authorization/google") ||
//                        request.getServletPath().equals("/oauth2/authorization/kakao") ||
//                        request.getServletPath().equals("/oauth2/authorization/naver") ||
//                        request.getServletPath().equals("/login/oauth2/code/google") ||
//                        request.getServletPath().equals("/login/oauth2/code/naver") ||
//                        request.getServletPath().equals("/login/oauth2/code/kakao") ||
//                        request.getServletPath().equals("/posts") ||
//                        request.getServletPath().equals("/ws/notification") ||
//                        request.getServletPath().equals("/redis/set") ||
//                        request.getServletPath().equals("/redis/get") ||
//                        request.getServletPath().equals("/user/create") ||
//                        (request.getServletPath().equals("/user") && request.getMethod().equals("POST")) ||
//                        request.getServletPath().equals("/user/logout") ||
//                        request.getServletPath().equals("/ws"
//                        );
//
////                matcher.matches(request);
//
//    }
//}