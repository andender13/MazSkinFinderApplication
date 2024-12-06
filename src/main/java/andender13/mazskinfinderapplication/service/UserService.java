package andender13.mazskinfinderapplication.service;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import andender13.mazskinfinderapplication.enums.Role;
import andender13.mazskinfinderapplication.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findUserByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123123"));
            admin.setEmail("admin@gmail.com");
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        userRepository.save(user);
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

    public List<User> findAllSearchReady() {
        return userRepository.findAllSearchReady();
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
            if (isLoggedIn && user.getStatus().equals(AuthorizationStatus.REGISTERED)) {
                updateStatusById(user.getId(), AuthorizationStatus.AUTHORIZED);
                return true;
            }
        }
        return false;
    }

    public void updateStatusById(Long id, AuthorizationStatus status) {
        userRepository.updateStatusById(id, status);
    }

    public void updateTelegram(User user) {
        userRepository.updateTelegramById(user.getId(), user.getTelegram());
    }

    public User getUserByEmailIdAndStatus(String emailId, AuthorizationStatus status) {
        return userRepository.findByEmailIdAndStatus(emailId, status).orElse(null);
    }
}
