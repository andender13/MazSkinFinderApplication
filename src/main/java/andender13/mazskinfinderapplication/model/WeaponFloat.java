package andender13.mazskinfinderapplication.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeaponFloat {
    private float leftFloatBorder;
    private float rightFloatBorder;
}
