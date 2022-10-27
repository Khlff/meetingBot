package data;

import java.util.HashMap;

public class UsersInformation {
    private static HashMap<Long, User> data;

    public UsersInformation() {data = new HashMap<>();}

    public static boolean hasPhotoWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).getStatusOfPhoto();
    }

    public static boolean hasNameWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).getStatusOfName();
    }
    public static void updateStatusOfPhotoByUserId(Long chatId, boolean newStatus){
        data.get(chatId).setStatusOfPhoto(newStatus);
    }
    public static void updateStatusOfNameByUserId(Long chatId,boolean newStatus){
        data.get(chatId).setStatusOfName(newStatus);
    }
    public void update(Long chatId) {
        data.put(chatId, new User(true, true));
    }
}

