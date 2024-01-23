package io.github.thiagoMachado.extension.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Users implements Serializable {
  private static final long serialVersionUID =1L;


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long  id;
  private String nome;
  private String email;
  private String senha;

  @CreatedDate
  @Column(name = "created_Data")
  @JsonFormat(locale = "UTF-8")
  private LocalDateTime createdAT;

}
