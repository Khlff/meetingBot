package commands;

import data.Data;
import data.GetStatusHashmap;
import data.StatusOfUploadInformation;

import java.util.HashMap;

public class Create implements Command, CanHaveChatID, GetStatusHashmap {
    public static StatusOfUploadInformation statusOfUploadInformation;
    private Long chatId;
    public Create(StatusOfUploadInformation statusOfUploadInformation) {
        Create.statusOfUploadInformation = statusOfUploadInformation;
    }

    @Override
    public String getHelp() {return "Создать свой профиль /create";}

    @Override
    public boolean isActive(String commandName) {return "/create".equals(commandName);}

    @Override
    public void setChatId(Long chatId) {
        this.chatId=chatId;
    }
    @Override
    public String Execute() {
        if (!statusOfUploadInformation.hasPhotoWaitingUpdate(chatId))
            statusOfUploadInformation.update(chatId);
        if (!statusOfUploadInformation.hasNameWaitingUpdate(chatId))
            statusOfUploadInformation.update(chatId);
        return """
                Напиши своё имя и пришли фотокарточку...📝""";
    }

    @Override
    public HashMap<Long, Data> getHashMap() {
        return statusOfUploadInformation.getHashMap();
    }
}
