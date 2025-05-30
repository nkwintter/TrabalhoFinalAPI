package org.serratec.trabalho.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {
 
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<String> erros = new ArrayList<>();
		for (FieldError err: ex.getBindingResult().getFieldErrors()) {
			erros.add(err.getField() + ": " + err.getDefaultMessage());
		}
		
	RespostaErro respostaErro = new RespostaErro(status.value(),
	"Não conseguimos processar sua solicitação. Verifique os campos e tente novamente! ", LocalDateTime.now(), erros);

	return super.handleExceptionInternal(ex, respostaErro, headers, status, request);
	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex,
                                                                   HttpServletRequest request) {
        RespostaErro erro = new RespostaErro(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                LocalDateTime.now(),
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
	
@ExceptionHandler(PedidoNaoEncontrado.class)
public ResponseEntity<RespostaErro> handlePedidoNaoEncontrado(PedidoNaoEncontrado ex) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.NOT_FOUND.value(),
        "O pedido informado não existe. Tente novamente!",
        LocalDateTime.now(),
        List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
}

@ExceptionHandler(ValidacaoException.class)
public ResponseEntity<RespostaErro> handleValidacaoException(ValidacaoException ex,
                                                              HttpServletRequest request) {
    RespostaErro erro = new RespostaErro(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação. Por favor, tente novamente!",
            LocalDateTime.now(),
            List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
}

@ExceptionHandler(EmailException.class)
public ResponseEntity<RespostaErro> handleEmailException(EmailException ex) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.BAD_REQUEST.value(),
        "O email informado não está cadastrado ou é inválido. Por favor, tente novamente!",
        LocalDateTime.now(),
        List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
}

@ExceptionHandler(ProdutoNaoEncontradoException.class)
public ResponseEntity<RespostaErro> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.NOT_FOUND.value(),
        "Produto não encontrado. Por favor, tente novamente!",
        LocalDateTime.now(),
        List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
}

@ExceptionHandler(ClienteException.class)
public ResponseEntity<RespostaErro> handleClienteException(ClienteException ex) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.NOT_FOUND.value(),
        "Cliente inexistente ou dados incorretos. Por favor, tente novamente!",
        LocalDateTime.now(),
        List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
}

@ExceptionHandler(CategoriaNaoEncontradaException.class)
public ResponseEntity<RespostaErro> handleCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.NOT_FOUND.value(),
        "Categoria não encontrada. Por favor, tente novamente!",
        LocalDateTime.now(),
        List.of(ex.getMessage())
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<RespostaErro> handleAllUncaught(Exception ex, WebRequest request) {
    RespostaErro erro = new RespostaErro(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Erro interno no servidor",
        LocalDateTime.now(),
        List.of("Ocorreu um erro inesperado: " + ex.getMessage())
    );
    return ResponseEntity.internalServerError().body(erro);
}

}
