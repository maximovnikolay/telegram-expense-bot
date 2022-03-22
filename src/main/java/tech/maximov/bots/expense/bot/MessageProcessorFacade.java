package tech.maximov.bots.expense.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessorFacade {

    public SendMessage handleUpdate(Update update) {
        var message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(String.format("Did you say, %s?", update.getMessage().getText()));
        return message;
    }
}
