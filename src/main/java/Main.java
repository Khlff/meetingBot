import commands.*;
import db.DatabaseRepository;
import db.DatabasePostgreSQL;
import db.PG.DatabasePG;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            DatabaseRepository database = new DatabasePG(
                    "admin",
                    "admin",
                    "jdbc:postgresql://localhost:5432/tgbot"
            );

            CommandHandler commandHandler = new CommandHandler();

            commandHandler.registerCommand(new Start());
            commandHandler.registerCommand(new Create(database));
            commandHandler.registerCommand(new Rate(database));
            Help helpCommand = new Help();
            commandHandler.registerCommand(helpCommand);
            helpCommand.setList(commandHandler.getCommandList());

            botsApi.registerBot(new RatingBot(database, commandHandler.getCommandList()));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }
}