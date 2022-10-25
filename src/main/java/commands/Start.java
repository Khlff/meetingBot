package commands;

public class Start implements Command{
    @Override
    public String getHelp() {
        return "Начать работу /start";
    }

    @Override
    public boolean isActive(String commandName) {
        return commandName.equals("/start");
    }

    @Override
    public String Execute() {
        return """
            Привет, меня зовут MeetWithMe! Я помогу тебе найти собеседника.
            
            Давайте начнём! Если Вам нужна помощь, нажмите /help""";
    }
}
