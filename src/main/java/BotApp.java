import commands.*;

public class BotApp {

    //todo модификаторы
    private static Command[] commandList;

    static String defaultAnswer = "Команда не найдена";
    BotApp() {
        var repo = new PhotoRepository();

        // todo: принимать список команд в аргументы конструктора

        var helpCommand = new Help();
        var startCommand = new Start();
        commandList = new Command[]{startCommand, helpCommand};
        helpCommand.setList(commandList);
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