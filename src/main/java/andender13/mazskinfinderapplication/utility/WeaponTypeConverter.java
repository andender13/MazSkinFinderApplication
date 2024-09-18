package andender13.mazskinfinderapplication.utility;

import andender13.mazskinfinderapplication.enums.WeaponType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeaponTypeConverter implements Converter<String, WeaponType> {

    @Override
    public WeaponType convert(String source) {
        for (WeaponType type : WeaponType.values()) {
            if (type.getStringType().equals(source)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown weapon type: " + source);
    }
}
