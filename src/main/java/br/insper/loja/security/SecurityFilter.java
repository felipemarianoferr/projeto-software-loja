package br.insper.loja.security;

import br.insper.loja.login.LoginService;
import br.insper.loja.usuario.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        String token = request.getHeader("Authorization");

        // Valida token e recupera o usuário
        Usuario usuario;
        try {
            usuario = loginService.validateToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Torna o usuário acessível no request
        request.setAttribute("usuario", usuario);

        // Permite qualquer GET autenticado
        if (HttpMethod.GET.matches(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Permite POST se for ADMIN
        if (HttpMethod.POST.matches(method) && "ADMIN".equals(usuario.getPapel())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Caso contrário, bloqueia com 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
