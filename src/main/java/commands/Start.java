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
            Привет👋 Этот бот - соревнование фотокарточек.
            Загружай свою и оценивай чужие. Набирай оценки и попади в топ!🔥
            Давай начнём, если тебе нужна помощь, напиши /help""";
    }
}
