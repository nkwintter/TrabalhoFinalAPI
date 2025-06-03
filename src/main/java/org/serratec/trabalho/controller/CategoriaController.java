package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.dto.CategoriaDTO;
import org.serratec.trabalho.service.CategoriaService;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento das categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @Operation(
        summary = "Lista todas as categorias",
        description = "Retorna todas as categorias cadastradas no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categorias retornadas com sucesso",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaDTO.class)
                )
            }
        ),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso proibido"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "505", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar categoria por ID",
        description = "Retorna os dados de uma categoria a partir do seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaDTO> buscar(@PathVariable Long id) {
        CategoriaDTO dto = categoriaService.buscar(id);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar nova categoria",
        description = "Cria uma nova categoria no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<CategoriaDTO> inserir(@RequestBody @Valid CategoriaDTO categoriaInsDTO) {
        CategoriaDTO categoriaDTO = categoriaService.inserir(categoriaInsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar categoria",
        description = "Atualiza os dados de uma categoria existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) {
        CategoriaDTO atualizado = categoriaService.atualizar(id, categoriaDTO);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar categoria",
        description = "Remove uma categoria existente do sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.buscar(id);
        if (categoria != null) {
            categoriaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}