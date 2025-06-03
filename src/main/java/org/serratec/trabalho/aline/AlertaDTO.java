package org.serratec.trabalho.aline;

import java.time.LocalDateTime;

public class AlertaDTO {

	private Long id;
	private Long userId;
	private Long produtoId;
	private String email;
	private Boolean isActive;
	private LocalDateTime createdAt;

	public AlertaDTO() {
	}

	public AlertaDTO(Long userId, Long produtoId, String email) {
		this.userId = userId;
		this.produtoId = produtoId;
		this.email = email;
		this.isActive = true;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}