package tech.maximov.bots.expense.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ExpensesBot extends TelegramWebhookBot {
    private final String botPath;
    private final String botUsername;
    private final String botToken;

    private final MessageProcessorFacade messageProcessorFacade;

    public ExpensesBot(DefaultBotOptions options,
                       MessageProcessorFacade messageProcessorFacade,
                       String botPath,
                       String botUsername,
                       String botToken) {
        super(options);
        this.messageProcessorFacade = messageProcessorFacade;
        this.botPath = botPath;
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageProcessorFacade.handleUpdate(update);
    }
}
