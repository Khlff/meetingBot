package data;

public class User {
//    private String username;
//    private String photo;
//    private Long chatId;
    private Boolean statusOfWaitingPhoto;
    private Boolean statusOfWaitingName;

    public User(boolean statusOfWaitingName, boolean statusOfWaitingPhoto) {
//        this.username = username;
//        this.chatId = chatId;
//        this.photo = photo;
        this.statusOfWaitingName=statusOfWaitingName;
        this.statusOfWaitingPhoto=statusOfWaitingPhoto;
    }
    public boolean getStatusOfPhoto(){return statusOfWaitingPhoto;}
    public boolean getStatusOfName(){return statusOfWaitingName;}
    public void setStatusOfPhoto(Boolean newStatusOfPhoto){statusOfWaitingPhoto=newStatusOfPhoto;}
    public void setStatusOfName(Boolean newStatusOfName){statusOfWaitingName=newStatusOfName;}

}
