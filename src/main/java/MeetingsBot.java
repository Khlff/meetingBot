import data.UsersInformation;
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
        var message = new SendMessage();
        Long chatID = Long.valueOf(update.getMessage().getChatId().toString());
        message.setChatId(chatID);

        if (UsersInformation.hasPhotoWaitingUpdate(chatID) || UsersInformation.hasNameWaitingUpdate(chatID)) {
            if (update.hasMessage() && update.getMessage().hasPhoto()) {
                String answer;
                if (UsersInformation.hasPhotoWaitingUpdate(chatID)) {
                    List<PhotoSize> photos = update.getMessage().getPhoto();
                    PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
                    answer = botApplication.setUserPhoto(chatID, maxPhoto);
                } else answer = "Пришли своё имя!";
                message.setText(answer);

            } else if (update.hasMessage() && update.getMessage().hasText()) {
                String inputUsername = update.getMessage().getText();
                if (UsersInformation.hasNameWaitingUpdate(chatID))
                    message.setText(botApplication.setUserName(chatID, inputUsername));
                else message.setText("Пришли фотокарточку!");
            }

        } else if (update.hasMessage() && update.getMessage().hasText()) {
            message.setText(botApplication.commandHandler(update.getMessage().getText(), chatID));
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
