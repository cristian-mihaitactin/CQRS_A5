package com.ip.m1app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A M1bicicleta.
 */
@Entity
@Table(name = "m1bicicleta")
public class M1bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "serie", nullable = false)
    private String serie;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "tip")
    private String tip;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "pret", nullable = false)
    private Float pret;

    @Column(name = "descriere")
    private String descriere;

    @Min(value = 1950)
    @Column(name = "an_fabricatie")
    private Integer anFabricatie;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    @Column(name = "status", nullable = false)
    private Integer status;

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

    public M1bicicleta serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getModel() {
        return model;
    }

    public M1bicicleta model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTip() {
        return tip;
    }

    public M1bicicleta tip(String tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Float getPret() {
        return pret;
    }

    public M1bicicleta pret(Float pret) {
        this.pret = pret;
        return this;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public M1bicicleta descriere(String descriere) {
        this.descriere = descriere;
        return this;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getAnFabricatie() {
        return anFabricatie;
    }

    public M1bicicleta anFabricatie(Integer anFabricatie) {
        this.anFabricatie = anFabricatie;
        return this;
    }

    public void setAnFabricatie(Integer anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public Integer getStatus() {
        return status;
    }

    public M1bicicleta status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        M1bicicleta m1bicicleta = (M1bicicleta) o;
        if (m1bicicleta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), m1bicicleta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "M1bicicleta{" +
            "id=" + getId() +
            ", serie='" + getSerie() + "'" +
            ", model='" + getModel() + "'" +
            ", tip='" + getTip() + "'" +
            ", pret=" + getPret() +
            ", descriere='" + getDescriere() + "'" +
            ", anFabricatie=" + getAnFabricatie() +
            ", status=" + getStatus() +
            "}";
    }
}
