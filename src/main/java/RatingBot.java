import data.UsersInformation;
import db.Database;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class RatingBot extends TelegramLongPollingBot {
    private final String botToken = System.getenv("BOT_TOKEN");
    private final String botName = System.getenv("BOT_NAME");

    BotApp botApplication;

    public RatingBot(Database database) {
        this.botApplication = new BotApp(database);
    }


    @Override
    public void onUpdateReceived(Update update) {
        var message = new SendMessage();
        Long chatID = Long.valueOf(update.getMessage().getChatId().toString());
        message.setChatId(chatID);
        ResultSet resultSetOfPhotos = botApplication.database.getPhotoIdColumn();

        System.out.printf("Update from user: %s, message text: %s\n", chatID, update.getMessage().getText());

        if (UsersInformation.hasWaitingUpdate(chatID)) {
            if (update.getMessage().hasPhoto() && update.getMessage().getCaption() != null) {
                List<PhotoSize> photos = update.getMessage().getPhoto();
                PhotoSize maxPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

                String answer;
                try {
                    answer = botApplication.setInformation(chatID, update.getMessage().getCaption(), maxPhoto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                message.setText(answer);
            } else message.setText("\uD83D\uDD34Пришли своё имя и фотокарточку одним сообщением!");

        } else if (update.hasMessage() && update.getMessage().hasText() && UsersInformation.hasWaitingRate(chatID)) {
            String answer = update.getMessage().getText();
            if (Objects.equals(update.getMessage().getText(), "/stop")) {
                UsersInformation.updateStatusOfRate(chatID, false);
                message.setText("Оценка фотографий остановлена");
            } else if (answer.matches("\\d+") && Integer.parseInt(answer) <= 10 && Integer.parseInt(answer) > 0){
                message.setText("Если ещё захотите оценивать - /rate\nЕсли захотите прекратить оценку - /stop");
            } else {message.setText("Вам нужно оценить фотографию (1-10)\nИли напишите /stop, чтобы остановить оценку");}
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            message.setText(botApplication.commandHandler(update.getMessage().getText(), chatID));
        }

        if (UsersInformation.hasWaitingRate(chatID)) {
            final SendPhoto sendPhotoRequest = new SendPhoto();
            sendPhotoRequest.setChatId(String.valueOf(chatID));
            sendPhotoRequest.setPhoto(new InputFile("AgACAgIAAxkBAAIGM2NxB1cUcI42TV9PsaquGQJmgYY1AAKzxDEbI-WJS_BhonlEJ6EwAQADAgADeQADKwQ"));

//            if (resultSetOfPhotos != null) {
//                try {
//                    sendPhotoRequest.setPhoto(new InputFile(resultSetOfPhotos.getString("photo_id")));
//
//                    resultSetOfPhotos.next();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//        }else {
//                message.setText("Больше нет фотографий для оценивания");
//            }
            try {
                execute(sendPhotoRequest);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
