import commands.*;

public class BotApp {
    protected Command HelpCommand;
    protected Command StartCommand;
    public static Command[] commandList;

    static String defaultAnswer = "Команда не найдена";

    BotApp() {
        HelpCommand = new Help();
        StartCommand = new Start();
        commandList = new Command[]{StartCommand, HelpCommand};
        canHaveCommandList helpList = (canHaveCommandList) HelpCommand;
        helpList.setList(commandList);
    }
    public String commandHandler(String inputMessage){
        for (var i: commandList){
            if (i.isActive(inputMessage)) {
                return i.Execute();
            }
        }
        return defaultAnswer;
    }
}