import commands.*;
import data.UsersInformation;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;


public class BotApp {

    //todo модификаторы
    private static Command[] commandList;

    private final static String defaultAnswer = "Команда не найдена";
    CanHaveCommandList helpCommand;
    Command startCommand;
    Command createCommand;

    BotApp() {
        var repo = new UsersInformation();

        // todo: принимать список команд в аргументы конструктора

        helpCommand = new Help();
        startCommand = new Start();
        createCommand = new Create(repo);
        commandList = new Command[]{startCommand, helpCommand, createCommand};
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

    public String setUserPhoto(Long chatID, PhotoSize photo) {
        var photoId = photo.getFileId();
        UsersInformation.updateStatusOfPhotoByUserId(chatID, false);
        System.out.println(photoId);
        return "Фото загружено";
        // Записываем в бд chatId = user_id, photoId
    }

    public String setUserName(Long chatID, String name) {
        if (name.length() > 20) {
            return "Имя не может быть таким длинным";
        } else {
            UsersInformation.updateStatusOfNameByUserId(chatID, false);
            System.out.println(name);
            // Записываем в бд chatId, name
            return "Имя изменено";
        }
    }
}