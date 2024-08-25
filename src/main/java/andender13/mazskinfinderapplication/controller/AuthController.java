package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.dto.UserDto;
import andender13.mazskinfinderapplication.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(new ResponseMessage("User registered successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("User already exists", false));
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

    // DTO for response message
    static class ResponseMessage {
        private String message;
        private boolean success;

        public ResponseMessage(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
