package br.insper.loja.evento;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventoService {

    @Value("${spring.application.usuario.url}")
    private String usuarioBaseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public void salvarEvento(String usuario, String descricao) {
        Evento evento = new Evento();
        evento.setAcao(descricao);
        evento.setEmail(usuario);

        try {
            restTemplate.postForEntity(usuarioBaseUrl + "/api/evento", evento, Evento.class);
        } catch (Exception e) {
            System.out.println("Erro ao salvar evento: " + e.getMessage());
        }
    }
}
