package edu.miu.cs.cs425.fairfieldlibrarywebapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    @NotBlank(message = "Street cannot be blank")
    @Column(nullable = false)
    private String street;
    @NotBlank(message = "City cannot be blank")
    @Column(nullable = false)
    private String city;
    @NotBlank(message = "State cannot be blank")
    @Column(nullable = false)
    private String state;
    @NotBlank(message = "Zip cannot be blank")
    @Column(nullable = false)
    private String zip;
}
