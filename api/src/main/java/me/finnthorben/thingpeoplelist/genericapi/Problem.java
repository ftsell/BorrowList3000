package me.finnthorben.thingpeoplelist.genericapi;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A data type sent by the API when there has been a problem fulfilling the request
 */
@Data
@AllArgsConstructor
public class Problem implements Serializable {
    @NotBlank String title;
    @NotBlank String detail;
}
