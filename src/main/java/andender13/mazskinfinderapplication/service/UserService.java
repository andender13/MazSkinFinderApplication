package andender13.mazskinfinderapplication.service;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import andender13.mazskinfinderapplication.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return userRepository.findUserByUsername(username).get();
        }
        return null;
    }

    public User findUserByUsernameAndStatus(String username, AuthorizationStatus status) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return userRepository.findUserByUsername(username).get();
        }
        return null;
    }

    public User findUserByStatus(AuthorizationStatus authorizationStatus) {
        if (userRepository.findUserByStatus(authorizationStatus).isPresent()) {
            return userRepository.findUserByStatus(authorizationStatus).get();
        }
        return null;
    }

    public boolean registerUser(String username, String password, String email) {
        if (username == null || password == null || email == null) {
            return false;
        }
        boolean usernameExists = userRepository.findUserByUsername(username).isPresent();
        boolean emailExists = userRepository.findUserByEmail(email).isPresent();

        if (usernameExists || emailExists) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, email);

        userRepository.save(user);
        return true;
    }

    public boolean loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findUserByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean isLoggedIn = passwordEncoder.matches(password, user.getPassword());
            if (isLoggedIn) {
                userRepository.updateStatusById(user.getId(), AuthorizationStatus.AUTHORIZED);
                return true;
            }
        }
        return false;
    }

    public void updateTelegram(User user) {
        userRepository.updateTelegramById(user.getId(), user.getTelegram());
    }
}
