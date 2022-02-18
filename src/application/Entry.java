package application;

public class Entry {
    private User user;
    private Entry next;

    public Entry(){
        this.user = null;
        this.next = null;
    }

    public Entry(User user, Entry next){
        this.user = user;
        this.next = next;
    }

    public Entry getNext() {
        return next;
    }

    public void setNext(Entry next) {
        this.next = next;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

