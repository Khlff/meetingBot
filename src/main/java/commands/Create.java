package commands;
public class Create implements Command, CanHaveChatID {
    private final PhotoRepository photoRepository;
    private Long chatId;
    public Create(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public String getHelp() {return "Создать свой профиль /create";}

    @Override
    public boolean isActive(String commandName) {return "/create".equals(commandName);}


    @Override
    public void setChatId(Long chatId) {
        this.chatId=chatId;
    }
    @Override
    public String Execute() {
        if (!photoRepository.hasPhotoWaitingUpdate(chatId))
            photoRepository.update(chatId);
        return "Пришлите фото";
    }
}
