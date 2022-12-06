package commands;

public interface CanHaveCommandList extends Command{
    void setList(Command[] commands);
}
