package org.serratec.trabalho.aline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AlertaService {

	// Lista em memória para simular banco de dados
	private List<AlertaDTO> alertas = new ArrayList<>();
	private Long nextId = 1L;

	// Buscar todos os alertas
	public List<AlertaDTO> buscarTodos() {
		return new ArrayList<>(alertas);
	}

	// Buscar alerta por ID
	public AlertaDTO buscar(Long id) {
		return alertas.stream().filter(alerta -> alerta.getId().equals(id)).findFirst().orElse(null);
	}

	// Buscar alertas por usuário
	public List<AlertaDTO> buscarPorUsuario(Long userId) {
		return alertas.stream().filter(alerta -> alerta.getUserId().equals(userId)).collect(Collectors.toList());
	}

	// Buscar alertas por produto
	public List<AlertaDTO> buscarPorProduto(Long produtoId) {
		return alertas.stream().filter(alerta -> alerta.getProdutoId().equals(produtoId)).collect(Collectors.toList());
	}

	// Inserir novo alerta
	public AlertaDTO inserir(AlertaDTO alertaDTO) {
		alertaDTO.setId(nextId++);
		alertaDTO.setIsActive(true);
		alertaDTO.setCreatedAt(LocalDateTime.now());
		alertas.add(alertaDTO);
		return alertaDTO;
	}

	// Atualizar alerta
	public AlertaDTO atualizar(Long id, AlertaDTO alertaDTO) {
		AlertaDTO alertaExistente = buscar(id);
		if (alertaExistente != null) {
			alertaExistente.setIsActive(alertaDTO.getIsActive());
			return alertaExistente;
		}
		return null;
	}

	// Deletar alerta por ID
	public void deleteById(Long id) {
		alertas.removeIf(alerta -> alerta.getId().equals(id));
	}

	// Contar alertas ativos
	public long contarAtivos() {
		return alertas.stream().filter(alerta -> alerta.getIsActive()).count();
	}
}
