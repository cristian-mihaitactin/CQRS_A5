package com.ip.m1app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the M1bicicleta entity.
 */
public class M1bicicletaDTO implements Serializable {

    private Long id;

    @NotNull
    private String serie;

    @NotNull
    private String model;

    private String tip;

    @NotNull
    @DecimalMin(value = "1")
    private Float pret;

    private String descriere;

    @Min(value = 1950)
    private Integer anFabricatie;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    private Integer status;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getAnFabricatie() {
        return anFabricatie;
    }

    public void setAnFabricatie(Integer anFabricatie) {
        this.anFabricatie = anFabricatie;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        M1bicicletaDTO m1bicicletaDTO = (M1bicicletaDTO) o;
        if(m1bicicletaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), m1bicicletaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "M1bicicletaDTO{" +
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
