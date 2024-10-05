package andender13.mazskinfinderapplication.utility.skinParser.mail;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAuthenticationTokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
