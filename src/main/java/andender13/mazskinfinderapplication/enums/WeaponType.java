package andender13.mazskinfinderapplication.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum WeaponType {
    AK_47("AK-47", List.of("Redline", "Case hardened")),
    M4A4("M4A4", List.of("Temukau", "Poseidon")),
    USP_S("USP-S", List.of("whiteout")),
    AWP("AWP", List.of("Fade", "Dragon lore")),
    M4A1_S("M4A1-S", List.of("Knight", "Atomic allow")),
    GALIL_AR("GALIL AR", List.of("Eco", "Chromatic aberration"));

    private final String stringType;
    private final List<String> skinNames;

    private static final Map<String, WeaponType> lookup = new HashMap<>();

    static {
        for (WeaponType type : WeaponType.values()) {
            lookup.put(type.getStringType(), type);
        }
    }

    public static WeaponType getTypeFromString(String stringType) {
        return lookup.get(stringType);
    }
}
