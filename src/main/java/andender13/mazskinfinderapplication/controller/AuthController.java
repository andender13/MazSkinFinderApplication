package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.dto.ResponseMessageDto;
import andender13.mazskinfinderapplication.dto.UserDto;
import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import andender13.mazskinfinderapplication.service.MailService;
import andender13.mazskinfinderapplication.service.UserService;
import andender13.mazskinfinderapplication.utility.skinParser.mail.HunterIoEmailVerifier;
import andender13.mazskinfinderapplication.utility.skinParser.mail.UserAuthenticationTokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MailService mailService;

    private final UserService userService;
    @Autowired
    private HunterIoEmailVerifier hunterIoEmailVerifier;
    @Autowired
    private AuthenticationManager authenticationManager;  // Изменено на AuthenticationManager

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseMessageDto> registerUser(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null || userDto.getEmail() == null) {
            return ResponseEntity.badRequest().body(new ResponseMessageDto("Invalid input", false));
        }

        boolean isSuccess = userService.registerUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        if (isSuccess) {
            String token = UserAuthenticationTokenGenerator.generateToken();
            User user = userService.findUserByUsername(userDto.getUsername());
            user.setEmailId(token);
            user.setStatus(AuthorizationStatus.WAITING_FOR_MAIL);
            userService.saveUser(user);

            if (hunterIoEmailVerifier.verifyEmail(user.getEmail())) {
                String link = "http://localhost:8080/api/auth/confirm?token=" + token;

                String subject = "Email Confirmation";
                String body = "Click the following link to confirm your email:\n " + link;

                mailService.sendMail(userDto.getEmail(), subject, body);
                return ResponseEntity.ok(new ResponseMessageDto("User registered successfully, confirm your email address", true));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDto("Your email doesn't exist", false));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDto("User already exists", false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password must not be empty");
        }
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(),
                    userDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authReq);

            // Устанавливаем аутентификацию в SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            SecurityContext context = SecurityContextHolder.getContext();
            System.out.println("SecurityContext до сохранения: " + context.getAuthentication());

            securityContext.setAuthentication(authentication);
            User user = userService.findUserByUsername(userDto.getUsername());
            userService.updateStatusById(user.getId(), AuthorizationStatus.AUTHORIZED);
            // Сохраняем SecurityContext в сессии
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            System.out.println("Is Authenticated: " + authentication.isAuthenticated());
            System.out.println("Is Anonymous: " + (authentication instanceof AnonymousAuthenticationToken));

            return ResponseEntity.ok("User logged in successfully");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        User user = userService.getUserByEmailIdAndStatus(token, AuthorizationStatus.WAITING_FOR_MAIL);
        if (user != null) {
            user.setStatus(AuthorizationStatus.REGISTERED);
            userService.saveUser(user);
            return ResponseEntity.ok("Email confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }
}
