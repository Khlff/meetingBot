import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyAmazingBot extends TelegramLongPollingBot {

    //todo: модификаторы доступа ✅
    private String token = System.getenv("TELEGRAM_BOT_TOKEN");

    @Override
    public void onUpdateReceived(Update update) {
        BotLogic botLogic = new BotLogic();
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(botLogic.answer(update.getMessage().getText()));

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "myamazingbot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
