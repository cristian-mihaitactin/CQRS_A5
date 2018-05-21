package com.rentapp.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Bicicleta entity.
 */
public class BicicletaDTO implements Serializable {

    private Long id;

    @NotNull
    private String serie;

    @NotNull
    private Double pret;

    private LocalDate data_inchiriere;

    private Integer timp_inchiriere;

    private LocalDate data_return;

    @NotNull
    private Integer status;

    private String cnp_renter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public LocalDate getData_inchiriere() {
        return data_inchiriere;
    }

    public void setData_inchiriere(LocalDate data_inchiriere) {
        this.data_inchiriere = data_inchiriere;
    }

    public Integer getTimp_inchiriere() {
        return timp_inchiriere;
    }

    public void setTimp_inchiriere(Integer timp_inchiriere) {
        this.timp_inchiriere = timp_inchiriere;
    }

    public LocalDate getData_return() {
        return data_return;
    }

    public void setData_return(LocalDate data_return) {
        this.data_return = data_return;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCnp_renter() {
        return cnp_renter;
    }

    public void setCnp_renter(String cnp_renter) {
        this.cnp_renter = cnp_renter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BicicletaDTO bicicletaDTO = (BicicletaDTO) o;
        if(bicicletaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bicicletaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BicicletaDTO{" +
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
