import java.util.Objects;

public class BotLogic {
    public String get_answer(String message) {
        return switch (parseUserMessage(message)) {
            case start -> DefaultAnswers.StartAnswer;
            case help -> DefaultAnswers.HelpAnswer;
            default -> DefaultAnswers.defaultAnswer;
        };
    }


    private UserRequest.UserRequests parseUserMessage(String message) {
        if (Objects.equals(message, StandardUserRequest.help)) {
            return UserRequest.UserRequests.help;
        } else if (Objects.equals(message, StandardUserRequest.start)) {
            return UserRequest.UserRequests.start;
        } else if

    }
}

