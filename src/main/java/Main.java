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
            botsApi.registerBot(new MeetingsBot(database));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }
}