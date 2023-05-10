package br.com.jonatas.apilogin.config;

import br.com.jonatas.apilogin.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (TokenUtil.ContainsToken(request.getHeader("Authorization"))) {
            System.out.println("tem token");
        }

        System.out.println("passou o filtro");
        filterChain.doFilter(request, response);

    }
}
