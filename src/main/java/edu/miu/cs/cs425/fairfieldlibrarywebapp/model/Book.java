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
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "ISBN cannot be blank")
    @Column(unique = true, nullable = false)
    private String isbn;
    @NotBlank(message = "Author cannot be blank")
    @Column(nullable = false)
    private String author;
    @NotBlank(message = "Publisher cannot be blank")
    @Column(nullable = false)
    private String publisher;
    private String location;
    private Integer totalCopy = 0;
    private Integer availableCopy = 0;

}
