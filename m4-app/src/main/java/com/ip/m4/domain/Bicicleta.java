package com.ip.m4.domain;


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
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "stare", nullable = false)
    private Integer stare;

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

    public Integer getStare() {
        return stare;
    }

    public Bicicleta stare(Integer stare) {
        this.stare = stare;
        return this;
    }

    public void setStare(Integer stare) {
        this.stare = stare;
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
            ", stare=" + getStare() +
            "}";
    }
}
