package br.insper.loja.produto;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService {

    public Produto getProduto(String id) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate
                    .getForEntity("http://54.207.148.33:8082/api/produto" + id,
                            Produto.class)
                    .getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void atualizarEstoque(String id, Integer quantidade) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://54.207.148.33:8082/api/produto/produtos/" + id + "/estoque";

        try {
            restTemplate.put(url, quantidade);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
    }


}
