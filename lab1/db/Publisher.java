package db;

import java.util.ArrayList;
import java.util.List;

import observation.AbstractObserver;
import observation.Observee;

class ControlledUser extends User {
    String publisherName;

    public ControlledUser(String name, String password, String publisherName) {
        super(name, password);
        this.publisherName = publisherName;
    }

    @Override
    public void task(String message) {
        this.messageList.add(message + " from " + publisherName);
    }
}

public class Publisher implements Observee<String> {
    String name;

    public String getName() {
        return name;
    }

    String password;
    List<AbstractObserver<String>> clients;

    public Publisher(String name, String password) {
        super();
        this.name = name;
        this.password = password;
        this.clients = new ArrayList<AbstractObserver<String>>();
    }

    public User createControlledObserver(String name, String password) {
        User user = new ControlledUser(name, password, this.name);
        MemoryDb.addUser(user);
        return user;
    }

    @Override
    public List<AbstractObserver<String>> getObservers() {
        return clients;
    }

    @Override
    public void addObserver(AbstractObserver<String> observer) {
        clients.add(observer);
    }

    @Override
    public void notify(String message) {
        for (AbstractObserver<String> observer : clients) {
            observer.task(message);
        }
    }

    @Override
    public void removeObserver(AbstractObserver<String> observer) {
        clients.remove(observer);
    }

}
