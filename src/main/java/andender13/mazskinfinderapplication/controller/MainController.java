package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.enums.AuthorizationStatus;
import andender13.mazskinfinderapplication.enums.StatTrack;
import andender13.mazskinfinderapplication.enums.WeaponQuality;
import andender13.mazskinfinderapplication.service.SkinService;
import andender13.mazskinfinderapplication.service.UserService;
import andender13.mazskinfinderapplication.service.WeaponService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private UserService userService;
    @Autowired
    private SkinService skinService;

    @GetMapping("/")
    public String mazSkinFinderSite(Model model, HttpSession session) {
        model.addAttribute("skins", weaponService.getAllWeapons());
        User user = userService.findUserByUsernameAndStatus(
                String.valueOf(session.getAttribute("username")),
                AuthorizationStatus.AUTHORIZED);
        boolean isAuthorized = user != null;
        model.addAttribute("LoggedIn", isAuthorized);
        model.addAttribute("weaponTypes", skinService.getAllGunTypes());
        model.addAttribute("weapon", new Weapon());
        model.addAttribute("weaponQualities", WeaponQuality.values());
        model.addAttribute("statTrack", StatTrack.values());
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

    @GetMapping("/profile")
    public String showProfileF(Model model, HttpSession session) {
        User user = userService.findUserByUsername(session.getAttribute("username").toString());
        model.addAttribute("user", user);
        return "profile";
    }


}
