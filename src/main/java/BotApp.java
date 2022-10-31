import commands.*;
import data.UsersInformation;
import db.Database;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.sql.SQLException;


public class BotApp {

    //todo модификаторы
    private static Command[] commandList;

    private final static String defaultAnswer = "Команда не найдена. Введите /help для просмотра команд.";
    CanHaveCommandList helpCommand;
    Command startCommand;
    Command createCommand;
    Command rateCommand;

    Database database;
    BotApp(Database database) {
        var repo = new UsersInformation();
        this.database = database;
        // todo: принимать список команд в аргументы конструктора

        helpCommand = new Help();
        startCommand = new Start();
        createCommand = new Create(repo);
        rateCommand = new Rate();
        commandList = new Command[]{startCommand, helpCommand, createCommand,rateCommand};
        helpCommand.setList(commandList);
    }

    public String commandHandler(String inputMessage, Long chatID) {
        for (var iterCommand : commandList) {
            if (iterCommand.isActive(inputMessage)) {
                if (iterCommand instanceof CanHaveChatID chatIDSetter)
                    chatIDSetter.setChatId(chatID);

                return iterCommand.Execute();
            }
        }
        return defaultAnswer;
    }

    public String setInformation(Long chatID, String name, PhotoSize photo) throws SQLException {
        if (photo == null) return "\uD83D\uDD34Ошибка фото";
        var photoId = photo.getFileId();

        if (name.length() > 20)
            return "\uD83D\uDD34Имя не может быть таким длинным";

        UsersInformation.updateStatus(chatID, false);
        database.updateToDb(chatID,name,photoId);
        // Записываем в бд chatId, name
        return "Профиль изменён✅";
    }
}
