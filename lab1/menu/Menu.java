package menu;

import java.util.List;

import db.MemoryDb;
import db.Publisher;
import db.User;

public class Menu {
    private Menu() {
    }

    static private void line() {
        System.out.println("------------------------------------------------");
    }

    static public void showMenu() {
        int choice = 0;
        do {
            line();
            System.out.println("1. Publisher login");
            System.out.println("2. User login");
            System.out.println("3. Publisher Sign up");
            System.out.println("4. User Sign up");
            System.out.println("5. Exit");
            choice = Input.readInt("Select option: ");
            if (choice == 1) {
                publisherLogin();
            }
            if (choice == 2) {
                userLogin();
            }
            if (choice == 3) {
                publisherSignUp();
            }
            if (choice == 4) {
                userSignUp();
            }
            line();
        } while (choice != 5);
    }

    static private void userSignUp() {
        line();
        String name = Input.readString("Enter user name: ");
        String password = Input.readString("Enter user password");
        line();
        MemoryDb.addUser(new User(name, password));
    }

    static private void publisherSignUp() {
        line();
        String name = Input.readString("Enter publisher name: ");
        String password = Input.readString("Enter publisher password");
        line();
        MemoryDb.addPublisher(new Publisher(name, password));
    }

    static private void userLogin() {
        line();
        String name = Input.readString("Enter user name: ");
        String password = Input.readString("Enter user password");
        line();
        User user = MemoryDb.userLogin(name, password);
        if (user != null) {
            System.out.println("Login success");
            loggedInUser(user);
        } else {
            System.out.println("Login failed");
        }
    }

    static private void loggedInUser(User user) {
        int choice = 0;
        do {
            line();
            System.out.println("1. View messages");
            System.out.println("2. Subscribe to publisher");
            System.out.println("3. Exit");
            choice = Input.readInt("Select Option: ");
            line();
            if (choice == 1) {
                for (String message : user.getMessageList()) {
                    System.out.println(message);
                }
            }
            if (choice == 2) {
                List<Publisher> publishers = MemoryDb.getPublishers();
                for (int i = 0; i < publishers.size(); i++) {
                    System.out.println(i + 1 + ". " + publishers.get(i).getName());
                }
                int publisherIndex = Input.readInt("Select publisher: ") - 1;
                if (publisherIndex >= 0 && publisherIndex < publishers.size()) {
                    publishers.get(publisherIndex).addObserver(user);
                } else {
                    System.out.println("Invalid publisher");
                }
            }
        } while (choice != 3);
    }

    static private void publisherLogin() {
        line();
        String name = Input.readString("Enter publisher name: ");
        String password = Input.readString("Enter publisher password");
        line();
        Publisher publisher = MemoryDb.publisherLogin(name, password);
        if (publisher != null) {
            System.out.println("Login success");
            loggedInPublisher(MemoryDb.publisherLogin(name, password));
        } else {
            System.out.println("Login failed");
        }
    }

    static private void loggedInPublisher(Publisher publisher) {
        int choice = 0;
        do {
            line();
            System.out.println("1. Send new message");
            System.out.println("2. Add controlled user");
            System.out.println("3. Exit");
            choice = Input.readInt("Select Option: ");
            line();
            if (choice == 1) {
                String message = Input.readString("Enter message: ");
                publisher.notify(message);
                continue;
            }
            if (choice == 2) {
                String name = Input.readString("Enter user name: ");
                String password = Input.readString("Enter user password: ");
                publisher.addObserver(publisher.createControlledObserver(name, password));
                continue;
            }
        } while (choice != 3);
    }

}
