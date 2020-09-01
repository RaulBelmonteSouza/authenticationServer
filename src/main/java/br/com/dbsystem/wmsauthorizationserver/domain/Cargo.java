package br.com.dbsystem.wmsauthorizationserver.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
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
@EqualsAndHashCode(of = "nome")
@ToString(exclude = {"usuarios", "privilegios"})
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "seguranca_cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Length(max = 60)
    @Column(length = 60, nullable = false, unique = true)
    private String nome;

    @Valid
    @ManyToMany(mappedBy = "cargos")
    private List<Usuario> usuarios = new ArrayList<>();

    @Valid
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "seguranca_cargos_privilegios",
            joinColumns = @JoinColumn(
                    name = "cargo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegio_id", referencedColumnName = "id"))
    private Set<Privilegio> privilegios = new HashSet<>();

    public void addPrivilegio(Privilegio privilegio) {
        this.privilegios.add(privilegio);
    }

    public void intersectionPrivilegios(Set<Privilegio> privilegios) {
        this.privilegios.retainAll(privilegios);
        this.privilegios.addAll(privilegios);
    }
}
