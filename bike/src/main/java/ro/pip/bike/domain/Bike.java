package ro.pip.bike.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bike.
 */
@Entity
@Table(name = "bike")
public class Bike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "model")
    private String model;

    @NotNull
    @DecimalMin(value = "5")
    @DecimalMax(value = "200")
    @Column(name = "price", nullable = false)
    private Double price;

    @Size(max = 40)
    @Column(name = "manufacturer", length = 40)
    private String manufacturer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public Bike model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public Bike price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Bike manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        Bike bike = (Bike) o;
        if (bike.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bike{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", price=" + getPrice() +
            ", manufacturer='" + getManufacturer() + "'" +
            "}";
    }
}
