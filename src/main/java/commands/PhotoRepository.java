package commands;

import java.util.HashMap;

public class PhotoRepository {
    private final HashMap<Long, Data> data;

    public PhotoRepository() {
        data = new HashMap<>();
    }


    public boolean hasPhotoWaitingUpdate(Long chatId) {
        if (!data.containsKey(chatId))
            return false;
        return data.get(chatId).statusOfWaitingPhoto;
    }

    public void update(Long chatId) {
        data.put(chatId,new Data(true));
        System.out.println(data);
        // db execute update
    }
}

class Data {
    Boolean statusOfWaitingPhoto;
    Data(boolean status){
        statusOfWaitingPhoto=status;
    }
}
