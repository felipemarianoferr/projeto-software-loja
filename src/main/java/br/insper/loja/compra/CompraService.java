package br.insper.loja.compra;

import br.insper.loja.evento.EventoService;
import br.insper.loja.produto.Produto;
import br.insper.loja.produto.ProdutoService;
import br.insper.loja.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ProdutoService produtoService;

    public Compra salvarCompra(Compra compra, Usuario usuario) {
        compra.setNome(usuario.getNome());
        compra.setUsuario(usuario.getEmail());
        compra.setDataCompra(LocalDateTime.now());

        List<String> idProdutos = new ArrayList<>();
        for (String idProduto : compra.getProdutos()) {
            Produto produto = produtoService.getProduto(idProduto);
            if (produto.getEstoque() < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            idProdutos.add(idProduto);
            produtoService.atualizarEstoque(produto.getId(), 1);
        }

        compra.setProdutos(idProdutos);
        eventoService.salvarEvento(usuario.getEmail(), "Compra realizada");

        return compraRepository.save(compra);
    }

    public List<Compra> getCompras() {
        return compraRepository.findAll();
    }
}
