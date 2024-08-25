package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import andender13.mazskinfinderapplication.service.UserService;
import andender13.mazskinfinderapplication.service.WeaponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {
    @Autowired
    WeaponService weaponService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String mazSkinFinderSite(Model model, HttpSession session) {
        model.addAttribute("skins", weaponService.getAllWeapons());
        User user = userService.findUserByUsernameAndStatus(
                String.valueOf(session.getAttribute("username")),
                AuthorizationStatus.AUTHORIZED);
        boolean isAuthorized = user != null;
        model.addAttribute("LoggedIn", isAuthorized);
        return "main";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "registerForm";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "loginForm";
    }

}
