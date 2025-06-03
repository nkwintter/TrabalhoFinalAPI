package org.serratec.trabalho.aline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alertas")
public class AlertaController {
    
    @Autowired
    private AlertaService alertaService;
    
    // GET /alertas - Listar todos os alertas
    @GetMapping
    public ResponseEntity<List<AlertaDTO>> listar(){
        return ResponseEntity.ok(alertaService.buscarTodos());
    }
    
    // GET /alertas/{id} - Buscar alerta específico
    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> buscar(@PathVariable Long id) {
        AlertaDTO dto = alertaService.buscar(id);
        
        if(dto != null) {
            return ResponseEntity.ok(dto);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    // GET /alertas/usuario/{userId} - Buscar alertas por usuário
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<AlertaDTO>> buscarPorUsuario(@PathVariable Long userId) {
        List<AlertaDTO> alertas = alertaService.buscarPorUsuario(userId);
        return ResponseEntity.ok(alertas);
    }
    
    // GET /alertas/produto/{produtoId} - Buscar alertas por produto
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<AlertaDTO>> buscarPorProduto(@PathVariable Long produtoId) {
        List<AlertaDTO> alertas = alertaService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(alertas);
    }
    
    // POST /alertas - Criar novo alerta
    @PostMapping
    public ResponseEntity<AlertaDTO> inserir(@RequestBody @Valid AlertaDTO alertaDTO){
        AlertaDTO novoAlerta = alertaService.inserir(alertaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAlerta);
    }
    
    // PUT /alertas/{id} - Atualizar alerta (ativar/desativar)
    @PutMapping("/{id}")
    public ResponseEntity<AlertaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaDTO alertaDTO) {
        AlertaDTO atualizado = alertaService.atualizar(id, alertaDTO);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE /alertas/{id} - Deletar alerta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        AlertaDTO alerta = alertaService.buscar(id);
        if (alerta != null) {
            alertaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
