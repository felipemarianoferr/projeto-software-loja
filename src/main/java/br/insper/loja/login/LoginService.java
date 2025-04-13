package br.insper.loja.login;

import br.insper.loja.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate<String, Usuario> tokens;

    public Usuario validateToken(String token) {
        Usuario usuario = tokens.opsForValue().get(token);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        return usuario;
    }
}
