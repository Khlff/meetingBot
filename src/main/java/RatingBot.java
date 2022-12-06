import commands.Command;
import repository.StockOfTables;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class RatingBot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botName = System.getenv("BOT_NAME");

    BotApp botApplication;
    StockOfTables database;

    public RatingBot(StockOfTables database, Command[] commandList) {
        this.botApplication = new BotApp(database, commandList);
        this.database = database;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = new SendMessage();
        Long chatID = Long.valueOf(update.getMessage().getChatId().toString());
        message.setChatId(chatID);
        System.out.printf("Update from user: %s, message text: %s\n", chatID, update.getMessage().getText());

//        try {
//            if (database.users.getStatusOfWaitingRate(chatID)){
//                final SendPhoto sendPhotoRequest = new SendPhoto();
//                sendPhotoRequest.setChatId(String.valueOf(chatID));
//                sendPhotoRequest.setPhoto(new InputFile("AgACAgIAAxkBAAIDZ2NiDo9zA5aoR7YIFpA9fqjkGDMXAALVvjEbpa4QS40P1Ww9cxAEAQADAgADeAADKgQ"));
//                try {
//                    execute(sendPhotoRequest);
//                } catch (final TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        if (update.getMessage().hasPhoto() && update.getMessage().getCaption() != null) {
            try {
                if (database.users.getStatusOfWaitingUpdate(chatID)) {
                    List<PhotoSize> photos = update.getMessage().getPhoto();
                    PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

                    String answer;
                    answer = botApplication.setInformation(chatID, update.getMessage().getCaption(), maxPhoto);
                    message.setText(answer);
                } else message.setText("\uD83D\uDD34Пришли своё имя и фотокарточку одним сообщением!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                message.setText(botApplication.commandHandler(update.getMessage().getText(), chatID));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
