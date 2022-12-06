package commands;

import java.util.ArrayList;

public interface CanHaveCommandList extends Command{
    void setList(ArrayList<Command> commands);
}
