package io.github.thiagoMachado.extension.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.github.thiagoMachado.extension.model.unums.ImageExtension;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // salva a data e hora que foi salvo a imagem, colocar uma anotação tbm
                                               // na class principal
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String name;
  private Long size;
  @Column
  @Enumerated(EnumType.STRING)
  private ImageExtension extension;
  @CreatedDate
  private LocalDateTime uploadDate;
  private String tags;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Users users;

  @Column
  @Lob
  private byte[] file;

  public String getFileName() {
    return getName().concat(".").concat(getExtension().name());
  }

  public void addAttribute(String string, List<Image> minhasCompras) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addAttribute'");
  }
}
