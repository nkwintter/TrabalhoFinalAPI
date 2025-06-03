package org.serratec.trabalho.aline;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alertas")
public class Alerta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "produto_id", nullable = false)
    private Long produtoId;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    

    public Alerta() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }
    
    public Alerta(Long userId, Long produtoId, String email) {
        this();
        this.userId = userId;
        this.produtoId = produtoId;
        this.email = email;
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
    

    public void ativar() {
        this.isActive = true;
    }
    
    public void desativar() {
        this.isActive = false;
    }
    
    public boolean isAtivo() {
        return this.isActive != null && this.isActive;
    }
    
    // toString para debug
    @Override
    public String toString() {
        return "Alerta{" +
                "id=" + id +
                ", userId=" + userId +
                ", produtoId=" + produtoId +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
    
    // equals e hashCode (JPA)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Alerta alerta = (Alerta) o;
        return id != null ? id.equals(alerta.id) : alerta.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}