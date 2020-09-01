package br.com.dbsystem.wmsauthorizationserver.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

    @CreatedBy
    @Column(name = "_created_by")
    private String usuarioCriacao;

    @CreatedDate
    @Column(name = "_created_at")
    private LocalDateTime dataCriacao;

    @LastModifiedBy
    @Column(name = "_updated_by")
    private String usuarioAtualizacao;

    @LastModifiedDate
    @Column(name = "_updated_at")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "_version")
    private Integer version;

}
