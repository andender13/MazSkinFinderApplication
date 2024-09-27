package andender13.mazskinfinderapplication.seleniumSearch;

import andender13.mazskinfinderapplication.entity.User;
import andender13.mazskinfinderapplication.entity.Weapon;
import andender13.mazskinfinderapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;

@EnableScheduling
@Component
public class TradeItGGSearchRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private TradeItGGSearch tradeItGGSearch;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Scheduled(fixedRate = 50000)
    void run()  {
        List<User> usersToSearch = userService.findAllSearchReady();

        for (User user : usersToSearch) {
            executorService.submit(() -> {
                List<Weapon> weapons = user.getWeapon(); // получаем скины пользователя
                for (Weapon weapon : weapons) {
                    tradeItGGSearch.startSearch(weapon); // запускаем поиск для каждого скина
                }
            });
        }

    }

    public void shutdown() {
        executorService.shutdown();
    }
}
