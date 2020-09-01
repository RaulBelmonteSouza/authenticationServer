package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "cargos")
@EqualsAndHashCode(of = {"operacao", "recurso"})
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seguranca_privilegios", uniqueConstraints = @UniqueConstraint(
        name = "unique_privilegio", columnNames = {"operacao", "recurso"}))
public class Privilegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Operacao operacao;

    @NonNull
    @NotBlank
    @Length(max = 50)
    @Column(length = 50, nullable = false)
    private String recurso;

    @NonNull
    @NotBlank
    @Length(max = 150)
    @Column(length = 150, nullable = false)
    private String descricao;

    @ManyToMany(mappedBy = "privilegios")
    private List<Cargo> cargos = new ArrayList<>();

    public String getAuthority() {
        return operacao + "_" + recurso.toUpperCase();
    }

    public enum Operacao {
        LEITURA, ESCRITA
    }
}
