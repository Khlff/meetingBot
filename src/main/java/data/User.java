package data;

public class User {
    private Boolean statusOfWaitingUpdate;

    public User(boolean statusOfWaitingUpdate) {
        this.statusOfWaitingUpdate = statusOfWaitingUpdate;
    }

    public boolean getStatusOfUpdate() {
        return statusOfWaitingUpdate;
    }

    public void setStatusOfWaitingUpdate(Boolean newStatusOfWaitingUpdate) {
        statusOfWaitingUpdate = newStatusOfWaitingUpdate;
    }


}
