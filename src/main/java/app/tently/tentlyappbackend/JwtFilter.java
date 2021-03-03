package app.tently.tentlyappbackend;

import app.tently.tentlyappbackend.services.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class JwtFilter extends BasicAuthenticationFilter {


    public TokenProvider tokenProvider;

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (tokenProvider == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            assert webApplicationContext != null;
            tokenProvider = webApplicationContext.getBean(TokenProvider.class);
        }

        String header = request.getHeader("Authorization");


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            if (!header.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            String token = header.replace("Bearer ", "");
            if (tokenProvider.validateToken(token)) {
                UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(token);
                SecurityContextHolder.getContext().setAuthentication(authResult);

            } else {
                throw new ServletException("Invalid token");
            }

        }
        chain.doFilter(request, response);


    }


    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(System.getenv("SIGNATURE_KEY").getBytes())
                .parseClaimsJws(header);

        String username = claimsJws.getBody().get("name").toString();
        String role = claimsJws.getBody().get("role").toString();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

        return new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
    }
}
