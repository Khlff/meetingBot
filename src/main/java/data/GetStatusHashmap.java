package data;

import commands.Command;

import java.util.HashMap;

public interface GetStatusHashmap extends Command {
    public HashMap<Long,Data> getHashMap();
}
