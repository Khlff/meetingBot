package data;

import java.util.HashMap;

public class StatusOfUploadInformation {
    private final HashMap<Long, Data> data;

    public HashMap<Long, Data> getHashMap() {
        return this.data;
    }

    public StatusOfUploadInformation() {
        data = new HashMap<>();
    }

    public boolean hasPhotoWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).statusOfWaitingPhoto;
    }

    public boolean hasNameWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).statusOfWaitingName;
    }

    public void update(Long chatId) {
        data.put(chatId, new Data(true, true));
    }
}

