package andender13.mazskinfinderapplication.controller;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/telegram")
public class TelegramNotificationController {
    @Autowired
    private UserService userService;

    @PostMapping("/setTelegram")
    public String setTelegram(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.updateTelegram(user);
        return "redirect:/profile";
    }
}
