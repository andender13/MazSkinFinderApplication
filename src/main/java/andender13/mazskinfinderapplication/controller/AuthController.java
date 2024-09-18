package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.dto.ResponseMessageDto;
import andender13.mazskinfinderapplication.dto.UserDto;
import andender13.mazskinfinderapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        boolean isSuccess = userService.registerUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        if (isSuccess) {
            return ResponseEntity.ok(new ResponseMessageDto("User registered successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDto("User already exists", false));
        }
    }

    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto, HttpSession session) {
        boolean isLogin = userService.loginUser(userDto.getUsername(), userDto.getPassword());
        if (isLogin) {
            session.setAttribute("username", userDto.getUsername());
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
