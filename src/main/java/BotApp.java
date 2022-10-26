import commands.*;
import data.Data;
import data.GetStatusHashmap;
import data.StatusOfUploadInformation;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.HashMap;


public class BotApp {

    //todo модификаторы
    private static Command[] commandList;

    private final static String defaultAnswer = "Команда не найдена";
    CanHaveCommandList helpCommand;
    Command startCommand;
    Command createCommand;

    BotApp() {
        var repo = new StatusOfUploadInformation();

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
    public HashMap<Long, Data> getHashMap(){
        if (createCommand instanceof GetStatusHashmap hashmap){
            return hashmap.getHashMap();
        }

        return null;
    }
    public void setUserPhoto(Long chatID, PhotoSize photo) {
        var photoId = photo.getFileId();
        System.out.println(photoId);
        // Записываем в бд chatId = user_id, photoId
    }
}