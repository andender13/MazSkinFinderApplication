package andender13.mazskinfinderapplication.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramNotificationBotConfig {
    @Value("${telegram.token}")
    private String botToken;
    @Value("${telegram.botName}")
    private String botName;


    @Bean
    public TelegramNotificationBot telegramNotificationBot() {
        TelegramNotificationBot bot = new TelegramNotificationBot(botToken, botName);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }

        return bot;
    }

}
