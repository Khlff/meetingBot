package commands;


import repository.StockOfTables;

import java.sql.SQLException;

public class Rate implements Command, CanHaveChatID {

    //Мы должны выдавать случайные картинки из базы данных и отправлять пользователю,
    //он ставит оценку от 1/10, считываем оценку, записываем в бд, присылаем новую картинку.
    //Так до тех пор пока пользователь не введёт выход

    StockOfTables database;
    private Long chatId;

    public Rate(StockOfTables database) {
        this.database = database;
    }

    @Override
    public String getHelp() {
        return "/rate - начать оценивать";
    }

    @Override
    public boolean isActive(String commandName) {
        return "/rate".equals(commandName);
    }

    @Override
    public String Execute() throws SQLException {
        if (!database.users.getStatusOfRating(chatId))
            database.users.setStatusOfRating(chatId, true);
        return "Если хочешь остановить оценивание напиши бла бла бла";
    }

    @Override
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
