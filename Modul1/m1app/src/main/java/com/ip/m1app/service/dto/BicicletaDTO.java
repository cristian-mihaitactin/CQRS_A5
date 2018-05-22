package com.ip.m1app.service.dto;


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
    @DecimalMin(value = "1")
    private Double pret;

    @NotNull
    private String tip;

    @NotNull
    private String model;

    @Min(value = 1980)
    private Integer anFabricatie;

    @NotNull
    @Min(value = 0)
    @Max(value = 4)
    private Integer status;

    private String descriere;

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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
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
            ", tip='" + getTip() + "'" +
            ", model='" + getModel() + "'" +
            ", anFabricatie=" + getAnFabricatie() +
            ", status=" + getStatus() +
            ", descriere='" + getDescriere() + "'" +
            "}";
    }
}
