package commands;

public interface canHaveCommandList extends Command{
    void setList(Command[] commands);
}
