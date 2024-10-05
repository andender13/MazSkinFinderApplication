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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String mazSkinFinderSite(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication при новом запросе: " + auth);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthorized = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        System.out.println(isAuthorized);
        if (isAuthorized) {
            String username = authentication.getName(); // Получаем имя пользователя
            User user = userService.findUserByUsernameAndStatus(username, AuthorizationStatus.AUTHORIZED);

            model.addAttribute("skins", weaponService.getAllWeapons());
            model.addAttribute("weaponTypes", skinService.getAllGunTypes());
            model.addAttribute("weapon", new Weapon());
            model.addAttribute("weaponQualities", WeaponQuality.values());
            model.addAttribute("statTrack", StatTrack.values());
            model.addAttribute("currentUser", user); // Передаем информацию о пользователе в модель
        }

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

    @GetMapping("/profile")
    public String showProfileF(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findUserByUsername(username);
            model.addAttribute("user", user);
        }
        return "profile"; // Возвращаем представление профиля
    }


}
