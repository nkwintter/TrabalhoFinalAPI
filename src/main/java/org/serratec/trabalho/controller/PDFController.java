package org.serratec.trabalho.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notasFiscais")
public class PDFController {

	@GetMapping("/{nomeArquivo}")
	public ResponseEntity<Resource> baixarNotaFiscal(@PathVariable String nomeArquivo) throws IOException {
	    Path caminho = Paths.get("/notasFiscais").resolve(nomeArquivo);
	    
	    if (!Files.exists(caminho)) {
	        return ResponseEntity.notFound().build();
	    }

	    Resource resource = new UrlResource(caminho.toUri());

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_PDF)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
	            .body(resource);
	}
}