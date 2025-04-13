package br.insper.loja.compra;

import br.insper.loja.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<Compra> getCompras() {
        return compraService.getCompras();
    }

    @PostMapping
    public Compra salvarCompra(@RequestBody Compra compra, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        return compraService.salvarCompra(compra, usuario);
    }
}
