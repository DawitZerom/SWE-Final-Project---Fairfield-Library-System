package edu.miu.cs.cs425.fairfieldlibrarywebapp.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRecordDTO {
    private Integer checkoutRecordId;
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
    @NotBlank(message = "Member number cannot be blank")
    private String memberNumber;
}
