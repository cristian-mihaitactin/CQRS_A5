package com.m3return.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Ordin.
 */
@Entity
@Table(name = "ordin")
public class Ordin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "serie_user", nullable = false)
    private String serie_user;

    @NotNull
    @Column(name = "data_inchiriere", nullable = false)
    private LocalDate data_inchiriere;

    @NotNull
    @Column(name = "id_bike", nullable = false)
    private Integer id_bike;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie_user() {
        return serie_user;
    }

    public Ordin serie_user(String serie_user) {
        this.serie_user = serie_user;
        return this;
    }

    public void setSerie_user(String serie_user) {
        this.serie_user = serie_user;
    }

    public LocalDate getData_inchiriere() {
        return data_inchiriere;
    }

    public Ordin data_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
        return this;
    }

    public void setData_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
    }

    public Integer getId_bike() {
        return id_bike;
    }

    public Ordin id_bike(Integer id_bike) {
        this.id_bike = id_bike;
        return this;
    }

    public void setId_bike(Integer id_bike) {
        this.id_bike = id_bike;
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
        Ordin ordin = (Ordin) o;
        if (ordin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordin{" +
            "id=" + getId() +
            ", serie_user='" + getSerie_user() + "'" +
            ", data_inchiriere='" + getData_inchiriere() + "'" +
            ", id_bike=" + getId_bike() +
            "}";
    }
}
