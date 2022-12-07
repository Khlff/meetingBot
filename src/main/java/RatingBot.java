import commands.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import repository.StockOfTables;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.InputFile;

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


        try {
            if (database.users.isUserExists(chatID)) {
                if (database.users.getStatusOfWaitingUpdate(chatID)) {
                    if (update.getMessage().hasPhoto() && update.getMessage().getCaption() != null) {
                        List<PhotoSize> photos = update.getMessage().getPhoto();
                        PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

                        String answer;
                        answer = botApplication.setInformation(chatID, update.getMessage().getCaption(), maxPhoto);
                        message.setText(answer);

                    } else message.setText("\uD83D\uDD34Пришли своё имя и фотокарточку одним сообщением!");


                } else if (database.users.getStatusOfRating(chatID)) {
                    if ("✅".equals(update.getMessage().getText()) || !database.users.getStatusOfWaitingRate(chatID)) {
                        long randomChatId = database.users.getRandomUserId(chatID);
                        String username = database.users.getUsernameByUserId(randomChatId);
                        String photoId = database.users.getPhotoIdByUserId(randomChatId);

                        final SendPhoto sendPhotoRequest = new SendPhoto();
                        sendPhotoRequest.setChatId(chatID);
                        sendPhotoRequest.setPhoto(new InputFile(photoId));
                        sendPhotoRequest.setCaption(username);
                        database.users.setStatusOfWaitingRate(chatID, true);
                        message.setText("лол");
                        execute(sendPhotoRequest);


                    } else if (database.users.getStatusOfWaitingRate(chatID) && update.getMessage().getText().matches("^(10|[1-9])$")) {
                        System.out.printf("Пользователь поставил оценку %s%n", update.getMessage().getText());
                        database.users.setStatusOfWaitingRate(chatID, false);
                        message.setText("Идём дальше..");

                    } else if ("стоп".equalsIgnoreCase(update.getMessage().getText())) {
                        database.users.setStatusOfRating(chatID, false);
                        database.users.setStatusOfWaitingRate(chatID, false);
                        message.setText("Оценка фотографий остановлена");
                    } else if (database.users.getStatusOfWaitingRate(chatID))
                        message.setText("Вам нужно оценить фотографию (1-10)\nИли напишите Стоп, чтобы остановить оценку");
                    else
                        message.setText(botApplication.commandHandler(update.getMessage().getText(), chatID));
                }
            } else
                message.setText(botApplication.commandHandler(update.getMessage().getText(), chatID));
        } catch (SQLException |
                 TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            execute(message);
        } catch (
                TelegramApiException e) {
            throw new RuntimeException(e);
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
