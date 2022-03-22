package tech.maximov.bots.expense.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import tech.maximov.bots.expense.bot.ExpensesBot;
import tech.maximov.bots.expense.bot.MessageProcessorFacade;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final BotConfig botConfig;

    @Bean
    public ExpensesBot expensesBot(MessageProcessorFacade messageProcessorFacade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        options.setProxyHost(botConfig.getProxyHost());
        options.setProxyPort(botConfig.getProxyPort());
        options.setProxyType(botConfig.getProxyType());

        return new ExpensesBot(options,
                messageProcessorFacade,
                botConfig.getWebHookPath(),
                botConfig.getUserName(),
                botConfig.getBotToken());
    }
}
