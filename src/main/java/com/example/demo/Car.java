package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "vehicles")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marka cannot be blank")
    @Column(name = "marka")
    private String marka;

    @NotBlank(message = "Model cannot be blank")
    @Column(name = "model")
    private String model;

    @Min(value = 1900, message = "Początek produkcji must be greater than or equal to 1900")
    @Column(name = "początek_produkcji")
    private Integer poczatekProdukcji;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPoczatekProdukcji() {
        return poczatekProdukcji;
    }

    public void setPoczatekProdukcji(Integer poczatekProdukcji) {
        this.poczatekProdukcji = poczatekProdukcji;
    }
}
