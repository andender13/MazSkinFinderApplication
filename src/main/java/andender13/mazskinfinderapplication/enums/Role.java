package andender13.mazskinfinderapplication.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    final String stringValue;

    Role(String stringValue) {
        this.stringValue = stringValue;
    }
}
