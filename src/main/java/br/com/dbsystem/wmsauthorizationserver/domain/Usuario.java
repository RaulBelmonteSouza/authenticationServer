package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "cargos", callSuper = true)
@EqualsAndHashCode(of = "codigo", callSuper = false)
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "seguranca_usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Length(max = 60)
    @Column(length = 60, nullable = false)
    private String nome;

    @NonNull
    @NaturalId
    @Column(nullable = false, unique = true)
    private Long codigo;

    @NonNull
    @NotBlank
    @Length(max = 100)
    @Column(length = 100, nullable = false)
    private String senha;

    @Column(nullable = false)
    private boolean habilitado = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seguranca_usuarios_cargos",
            joinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "cargo_id", referencedColumnName = "id"))
    private Set<Cargo> cargos = new HashSet<>();

    public void atualizaCargos(Collection<Cargo> cargos) {
        this.cargos.retainAll(cargos);
        this.cargos.addAll(cargos);
    }
}
