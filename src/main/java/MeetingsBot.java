import data.UsersInformation;
import db.Database;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class MeetingsBot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botName = System.getenv("BOT_NAME");
    Database database;

    public MeetingsBot(Database database) {
        this.database = database;
    }

    BotApp botApplication = new BotApp(database);

    @Override
    public void onUpdateReceived(Update update) {
        var message = new SendMessage();
        Long chatID = Long.valueOf(update.getMessage().getChatId().toString());
        message.setChatId(chatID);

        if (UsersInformation.hasWaitingUpdate(chatID)) {
            if (update.getMessage().hasPhoto() && update.getMessage().getCaption() != null) {
                List<PhotoSize> photos = update.getMessage().getPhoto();
                PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

                String answer = null;
                try {
                    answer = botApplication.setInformation(chatID, update.getMessage().getCaption(), maxPhoto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                message.setText(answer);
            } else message.setText("\uD83D\uDD34Пришли своё имя и фотокарточку одним сообщением!");

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
