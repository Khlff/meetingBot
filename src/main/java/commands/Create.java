package commands;


import db.DatabaseRepository;


public class Create implements Command, CanHaveChatID {
    private Long chatId;
    protected DatabaseRepository database;

    public Create(DatabaseRepository database) {
        this.database = database;
    }

    @Override
    public String getHelp() {
        return "/create - создать/изменить свой профиль";
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
        if (!(database.getStatusOfWaitingName(chatId) && database.getStatusOfWaitingPhoto(chatId))) {
            database.setStatusOfWaitingPhoto(chatId, true);
            database.setStatusOfWaitingName(chatId, true);
        }
        return """
                Пришли своё имя и фотокарточку одним сообщением...📝""";
    }
}
