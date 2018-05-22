package com.ip.m1app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bicicleta.
 */
@Entity
@Table(name = "bicicleta")
public class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "serie", nullable = false)
    private String serie;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "pret", nullable = false)
    private Double pret;

    @NotNull
    @Column(name = "tip", nullable = false)
    private String tip;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @Min(value = 1980)
    @Column(name = "an_fabricatie")
    private Integer anFabricatie;

    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "descriere")
    private String descriere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public Bicicleta serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Double getPret() {
        return pret;
    }

    public Bicicleta pret(Double pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public String getTip() {
        return tip;
    }

    public Bicicleta tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getModel() {
        return model;
    }

    public Bicicleta model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getAnFabricatie() {
        return anFabricatie;
    }

    public Bicicleta anFabricatie(Integer anFabricatie) {
        this.anFabricatie = anFabricatie;
        return this;
    }

    public void setAnFabricatie(Integer anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public Integer getStatus() {
        return status;
    }

    public Bicicleta status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescriere() {
        return descriere;
    }

    public Bicicleta descriere(String descriere) {
        this.descriere = descriere;
        return this;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bicicleta bicicleta = (Bicicleta) o;
        if (bicicleta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bicicleta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            ", pret=" + getPret() +
            ", tip='" + getTip() + "'" +
            ", model='" + getModel() + "'" +
            ", anFabricatie=" + getAnFabricatie() +
            ", status=" + getStatus() +
            ", descriere='" + getDescriere() + "'" +
            "}";
    }
}
