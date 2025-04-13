package br.insper.loja.produto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService {

    @Value("${spring.application.produto.url}")
    private String produtoBaseUrl; // → defina isso no application.properties

    private RestTemplate restTemplate = new RestTemplate();

    public Produto getProduto(String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Produto> response = restTemplate.exchange(
                produtoBaseUrl + "/api/produto/" + id,
                HttpMethod.GET,
                entity,
                Produto.class
        );

        return response.getBody();
    }

    public void atualizarEstoque(String id, Integer quantidade, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = produtoBaseUrl + "/api/produto/estoque/" + id + "/" + quantidade + "/false";

        try {
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
    }

}
