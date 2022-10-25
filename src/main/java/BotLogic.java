import java.util.Objects;

public class BotLogic {
    public String get_answer(String message) {
        return switch (parseUserMessage(message)) {
            case START -> DefaultAnswers.StartAnswer;
            case HELP -> DefaultAnswers.HelpAnswer;
            default -> DefaultAnswers.defaultAnswer;
        };
    }


    private UserRequest.UserRequests parseUserMessage(String message) {
        if (Objects.equals(message, StandardUserRequest.help)) {
            return UserRequest.UserRequests.HELP;
        } else if (Objects.equals(message, StandardUserRequest.start)) {
            return UserRequest.UserRequests.START;
        } else if (Objects.equals(message, StandardUserRequest.joke)) {
            return UserRequest.UserRequests.JOKE;
        }
        return UserRequest.UserRequests.TEACHER;
    }
}
