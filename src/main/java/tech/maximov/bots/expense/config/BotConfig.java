package tech.maximov.bots.expense.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String botPath;
    private String botUsername;
    private String botToken;
    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;
}
