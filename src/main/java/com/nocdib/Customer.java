package com.nocdib;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @SequenceGenerator(name="customer_id_sequence", sequenceName="customer_id_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    private Integer id;
    private String name;
    private String email;
    private Integer age;

    public Customer() {
        this(0, "John Doe", "anonymous@hotmail.com", 45);
    }

    public Customer(Integer id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("Customer(%d, %s, %s, %d)", id, name, email, age);
    }
}
