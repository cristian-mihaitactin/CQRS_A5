package ro.pip.bike.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Bike entity.
 */
public class BikeDTO implements Serializable {

    private Long id;

    private String model;

    @NotNull
    @DecimalMin(value = "5")
    @DecimalMax(value = "200")
    private Double price;

    @Size(max = 40)
    private String manufacturer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BikeDTO bikeDTO = (BikeDTO) o;
        if(bikeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bikeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BikeDTO{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", price=" + getPrice() +
            ", manufacturer='" + getManufacturer() + "'" +
            "}";
    }
}
