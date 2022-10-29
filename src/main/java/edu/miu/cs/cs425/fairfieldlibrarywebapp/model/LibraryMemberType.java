package edu.miu.cs.cs425.fairfieldlibrarywebapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LibraryMemberType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer libraryMemberTypeId;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private Integer maxLengthBorrowBook;
    @NotNull
    @Column(nullable = false)
    private Double overdueFee = 0.0;
}
