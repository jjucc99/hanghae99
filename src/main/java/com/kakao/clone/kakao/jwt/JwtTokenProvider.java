package com.kakao.clone.kakao.jwt;

import com.kakao.clone.kakao.model.User;
import com.kakao.clone.kakao.repository.UserRepository;
import com.kakao.clone.kakao.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // secretKey 와 같은 민감정보는 숨기는 것이 좋다. (이것은 연습이라서 노출함)
    @Value("K7kjHSF345h345S86F3A2erGB98iWIad")
    private String secretKey;

    // 토큰 유효시간 5분 설정 (1000L = 1초, 1000L * 60 = 1분)
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 120;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    // 객체 초기화, secretKey 를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        User user = userRepository.findByNickname(this.getUserPk(token))
                .orElseThrow(()-> new NullPointerException("Null is username"));

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

      /* UserDetails userDetails =userDetailsService.loadUserByUsername();
        Member member = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new NullPointerException("Null is username"));
        UserDetailsImpl userdetailsimpl = new UserDetailsImpl(member);
        */
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //토큰 시간 만료 시키기
    public boolean invalidateToken(String jwtToken) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken);
            Date now = new Date();
                    claims
                    .getBody()
                    .setExpiration(new Date(now.getTime() - (1000L * 60 * 30)));
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}