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
public class CheckinDTO {
    private Integer checkoutRecordId;
    @NotBlank(message = "Is checked-in cannot be null")
    private String isCheckedIn;
    // @NotBlank(message = "Overdue fee cannot be blank")
    // private double overdueFee;
}
