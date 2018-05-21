package com.rentapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    @Column(name = "pret", nullable = false)
    private Double pret;

    @Column(name = "data_inchiriere")
    private LocalDate data_inchiriere;

    @Column(name = "timp_inchiriere")
    private Integer timp_inchiriere;

    @Column(name = "data_return")
    private LocalDate data_return;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "cnp_renter")
    private String cnp_renter;

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

    public LocalDate getData_inchiriere() {
        return data_inchiriere;
    }

    public Bicicleta data_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
        return this;
    }

    public void setData_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
    }

    public Integer getTimp_inchiriere() {
        return timp_inchiriere;
    }

    public Bicicleta timp_inchiriere(Integer timp_inchiriere) {
        this.timp_inchiriere = timp_inchiriere;
        return this;
    }

    public void setTimp_inchiriere(Integer timp_inchiriere) {
        this.timp_inchiriere = timp_inchiriere;
    }

    public LocalDate getData_return() {
        return data_return;
    }

    public Bicicleta data_return(LocalDate data_return) {
        this.data_return = data_return;
        return this;
    }

    public void setData_return(LocalDate data_return) {
        this.data_return = data_return;
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

    public String getCnp_renter() {
        return cnp_renter;
    }

    public Bicicleta cnp_renter(String cnp_renter) {
        this.cnp_renter = cnp_renter;
        return this;
    }

    public void setCnp_renter(String cnp_renter) {
        this.cnp_renter = cnp_renter;
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
            ", data_inchiriere='" + getData_inchiriere() + "'" +
            ", timp_inchiriere=" + getTimp_inchiriere() +
            ", data_return='" + getData_return() + "'" +
            ", status=" + getStatus() +
            ", cnp_renter='" + getCnp_renter() + "'" +
            "}";
    }
}
