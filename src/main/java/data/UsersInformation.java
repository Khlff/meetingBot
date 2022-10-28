package data;

import java.util.HashMap;

public class UsersInformation {
    private static HashMap<Long, User> data;

    public UsersInformation() {
        data = new HashMap<>();
    }

    public static boolean hasWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).getStatusOfUpdate();
    }

    public static void updateStatus(Long chatId, boolean newStatus) {
        data.get(chatId).setStatusOfWaitingUpdate(newStatus);
    }

    public void update(Long chatId) {
        data.put(chatId, new User(true));
    }
}

