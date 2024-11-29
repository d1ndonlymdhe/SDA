package db;

import java.util.ArrayList;
import java.util.List;

import observation.AbstractObserver;

public class User implements AbstractObserver<String> {
    List<String> messageList;
    public String name;
    public String password;
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.messageList = new ArrayList<String>();
    }

    @Override
    public void task(String message) {
        this.messageList.add(message);
    }

    @Override
    public List<String> getMessageList() {
        return messageList;
    }
}