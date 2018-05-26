package com.ip.m4app.service.dto;


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
    @Min(value = 0)
    @Max(value = 10)
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
            ", status=" + getStatus() +
            "}";
    }
}
