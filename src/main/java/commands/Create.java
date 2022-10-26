package commands;
public class Create implements Command{
    @Override
    public String getHelp() {return "Создать свой профиль /create";}

    @Override
    public boolean isActive(String commandName) {return "/create".equals(commandName);}

    @Override
    public String Execute() {

    }
}
