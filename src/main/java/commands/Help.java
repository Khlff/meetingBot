package commands;

public class Help implements Command, CanHaveCommandList {
    @Override
    public String getHelp() {
        return "Вызов справки /help";
    }

    @Override
    public boolean isActive(String commandName) {
        return commandName.equals("/help");
    }


    private Command[] commandList;

    @Override
    public void setList(Command[] commands) {
        this.commandList = commands;
    }

    @Override
    public String Execute() {
        StringBuilder result = new StringBuilder().append("Вот что я могу:\n");
        if (this.commandList!=null){
            for (var i : this.commandList){
                result.append(i.getHelp()).append('\n');
            }
            return result.toString();
        }
        return null;
    }
}
