import java.util.Objects;

public class BotLogic {
    /*

        // UI
        // Application
        // Domain

            todo: Logic оч связана с UI
     */

    public String answer(String message){
        switch (parseUserMessage(message)) {
            case HELP:
                return StandardAnswer.answerForHelp;
            case START:
                return StandardAnswer.answerForStart;
            case JOKE:
                return StandardAnswer.answerForJoke;
            case TEACHER:
                return """
                        tg: @a_lgnv
                        Расписание:
                            Среда: 10:40 - 12:10 каб.517""";
        }
        return StandardAnswer.defaultAnswer;
    }


//  todo*: не хардкодить здесь команды, генироровать хелп только по тому, что точно умеем



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
