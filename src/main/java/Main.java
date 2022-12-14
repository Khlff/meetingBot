import commands.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.StockOfTables;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            StockOfTables database = new StockOfTables(
                    "postgres",
                    "KhlffAndI5432",
                    "jdbc:postgresql://localhost:5432/tgbot"
            );
            Help helpCommand = new Help();
            Start startCommand = new Start();
            Create createCommand = new Create(database);
            Rate rateCommand = new Rate(database);
            var commandList =new Command[]{startCommand, helpCommand, createCommand,rateCommand};
            helpCommand.setList(commandList);

            botsApi.registerBot(new RatingBot(database, commandList));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }
}