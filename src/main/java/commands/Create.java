package commands;


import data.UsersInformation;


public class Create implements Command, CanHaveChatID {
    public static UsersInformation usersInformation;
    private Long chatId;

    public Create(UsersInformation usersInformation) {
        Create.usersInformation = usersInformation;
    }

    @Override
    public String getHelp() {
        return "Создать свой профиль /create";
    }

    @Override
    public boolean isActive(String commandName) {
        return "/create".equals(commandName);
    }

    @Override
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String Execute() {
        if (!UsersInformation.hasPhotoWaitingUpdate(chatId))
            usersInformation.update(chatId);
        if (!UsersInformation.hasNameWaitingUpdate(chatId))
            usersInformation.update(chatId);
        return """
                Напиши своё имя и пришли фотокарточку...📝""";
    }
}
