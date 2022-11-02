import commands.*;
import db.Database;
import db.DatabasePostgreSQL;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Database database = new DatabasePostgreSQL(
                    "admin",
                    "admin",
                    "jdbc:postgresql://localhost:5432/tgbot");
            CommandHandler commandHandler = new CommandHandler();
            commandHandler.registerCommand(Command );
            helpCommand = new Help();
            startCommand = new Start();
            createCommand = new Create(repo);
            rateCommand = new Rate(repo);
            commandList = new Command[]{startCommand, helpCommand, createCommand,rateCommand};
            helpCommand.setList(commandList);

            botsApi.registerBot(new RatingBot(database, commandList));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }
}