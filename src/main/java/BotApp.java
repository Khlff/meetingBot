import commands.*;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.List;

public class BotApp {

    //todo модификаторы
    private static Command[] commandList;

    static String defaultAnswer = "Команда не найдена";
    BotApp() {
        var repo = new PhotoRepository();

        // todo: принимать список команд в аргументы конструктора

        var helpCommand = new Help();
        var startCommand = new Start();
        var createCommand = new Create(repo);
        commandList = new Command[]{startCommand, helpCommand,createCommand};
        helpCommand.setList(commandList);
    }
    public String commandHandler(String inputMessage, Long chatID){
        for (var iterCommand: commandList){
            if (iterCommand.isActive(inputMessage)) {
                if (iterCommand instanceof CanHaveChatID chatIDSetter)
                    chatIDSetter.setChatId(chatID);

                return iterCommand.Execute();
            }
        }
        return defaultAnswer;
    }

    public void setUserPhoto(Long chatID, List<PhotoSize> photo) {
    }
}