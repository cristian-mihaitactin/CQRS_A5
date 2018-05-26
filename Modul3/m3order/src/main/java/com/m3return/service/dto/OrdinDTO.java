package com.m3return.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ordin entity.
 */
public class OrdinDTO implements Serializable {

    private Long id;

    @NotNull
    private String serie_user;

    @NotNull
    private LocalDate data_inchiriere;

    @NotNull
    private Integer id_bike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie_user() {
        return serie_user;
    }

    public void setSerie_user(String serie_user) {
        this.serie_user = serie_user;
    }

    public LocalDate getData_inchiriere() {
        return data_inchiriere;
    }

    public void setData_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
    }

    public Integer getId_bike() {
        return id_bike;
    }

    public void setId_bike(Integer id_bike) {
        this.id_bike = id_bike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdinDTO ordinDTO = (OrdinDTO) o;
        if(ordinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdinDTO{" +
            "id=" + getId() +
            ", serie_user='" + getSerie_user() + "'" +
            ", data_inchiriere='" + getData_inchiriere() + "'" +
            ", id_bike=" + getId_bike() +
            "}";
    }
}
