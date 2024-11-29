package db;

import java.util.ArrayList;
import java.util.List;

public class MemoryDb {
    private static List<Publisher> publishers;
    private static List<User> users;

    private MemoryDb() {
    }

    private static void init() {
        if (publishers == null) {
            publishers = new ArrayList<Publisher>();
        }
        if (users == null) {
            users = new ArrayList<User>();
        }
    }

    public static Publisher publisherLogin(String name, String password) {
        init();
        for (Publisher publisher : publishers) {
            if (publisher.name.equals(name) && publisher.password.equals(password)) {
                return publisher;
            }
        }
        return null;
    }

    public static User userLogin(String name, String password) {
        init();
        for (User user : users) {
            if (user.name.equals(name) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void addPublisher(Publisher publisher) {
        init();
        publishers.add(publisher);
    }

    public static void addUser(User user) {
        init();
        users.add(user);
    }

    public static List<Publisher> getPublishers() {
        init();
        return publishers;
    }
}
