package commands;

import java.util.ArrayList;

public class Help implements Command, CanHaveCommandList {
    @Override
    public String getHelp() {
        return "/help - вызов справки";
    }

    @Override
    public boolean isActive(String commandName) {
        return commandName.equals("/help");
    }


    private ArrayList<Command> commandList;

    @Override
    public void setList(ArrayList<Command> commands) {
        this.commandList = commands;
    }

    @Override
    public String Execute() {
        StringBuilder result = new StringBuilder().append("You can control me by sending these commands:\n\n");
        if (this.commandList != null) {
            for (var i : this.commandList) {
                result.append(i.getHelp()).append('\n');
            }
            return result.toString();
        }
        return null;
    }
}
