package commands;

public class Rate implements Command{
    //Мы должны выдавать случайные картинки из базы данных и отправлять пользователю,
    //он ставит оценку от 1/10, считываем оценку, записываем в бд, присылаем новую картинку.
    //Так до тех пор пока пользователь не введёт выход

    @Override
    public String getHelp() {
        return "/rate - начать оценивать";
    }

    @Override
    public boolean isActive(String commandName) {
        return "/rate".equals(commandName);
    }

    @Override
    public String Execute() {
        return null;
    }
}
