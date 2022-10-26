import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MeetingsBot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botName = System.getenv("BOT_NAME");
    BotApp botApplication = new BotApp();

    @Override
    public void onUpdateReceived(Update update) {
//        BotLogic botLogic = new BotLogic();
        // Если произошёл апдейт сообщения и оно имеет текст:
        if (update.hasMessage() && update.getMessage().hasPhoto()){
            Long chatID = Long.valueOf(update.getMessage().getChatId().toString());
            var message = new SendMessage();
            message.setChatId(chatID);
            botApplication.setUserPhoto(chatID, update.getMessage().getPhoto());
            message.setText("Фото загружено");
            try {
                execute(message);
            } catch (TelegramApiException e){e.printStackTrace();}
        }

        else if (update.hasMessage() && update.getMessage().hasText()) {
            var chatID =update.getMessage().getChatId().toString();
            var message = new SendMessage();
            message.setChatId(chatID);
            message.setText(botApplication.commandHandler(update.getMessage().getText(), Long.valueOf(chatID)));

            // Пробуем отправить:
            try {
                execute(message);
            } catch (TelegramApiException e) {e.printStackTrace();}
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
