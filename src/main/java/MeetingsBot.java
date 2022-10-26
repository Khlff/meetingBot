import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;

public class MeetingsBot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botName = System.getenv("BOT_NAME");
    BotApp botApplication = new BotApp();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasPhoto()){
            Long chatID = Long.valueOf(update.getMessage().getChatId().toString());

            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

            var message = new SendMessage();
            message.setChatId(chatID);
            if (maxPhoto!=null){
                botApplication.setUserPhoto(chatID, maxPhoto);
                message.setText("Фото загружено");}
            else message.setText("Ошибка фото");

            try {execute(message);} catch (TelegramApiException e){e.printStackTrace();}
        }

        else if (update.hasMessage() && update.getMessage().hasText()) {
            var chatID =update.getMessage().getChatId().toString();
            var message = new SendMessage();
            message.setChatId(chatID);
            message.setText(botApplication.commandHandler(update.getMessage().getText(), Long.valueOf(chatID)));

            // Пробуем отправить:
            try {execute(message);} catch (TelegramApiException e) {e.printStackTrace();}
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
