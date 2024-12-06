# Objective: To implement design patterns (Singleton, Strategy, Factory, Observer)

## Theory:

- Singleton: The Singleton Pattern is a creational design pattern that ensures a class has only one instance and provides a global point of access to that instance. It is used in scenarios where exactly one object is needed to coordinate actions across a system, such as logging, configurations, database connections.  
   The key components of a singleton are:
  1. Static reference to a resource.
  2. Private constructor, the singleton class should only be accessed by its static methods, having a private constructor enforces that.
  3. Resource initializer.
  4. Static methods to access the resource.
- Strategy: The Strategy Pattern is a behavioral design pattern in software development. It allows you to define a family of algorithms, encapsulate each one, and make them interchangeable during runtime through dynamic dispatch.  
  The key components of strategy pattern are:
  1.  Context: The class that uses a strategy to perform a task.
  2.  Strategy: The interface that defines a common behavior.
  3.  Concrete Strategies: Class that implements the Strategy interface.
- Factory: The Factory Pattern is a creational design pattern used to create objects without specifying their exact class in the code.  
  The key components of factory pattern are:
  1.  Factory Method: A method responsible for creating objects of specific types.
  2.  Encapsulation of creation logic: The object creation logic is moved out of the client code and into the factory, reducing coupling.
  3.  Polymorphism: Subclasses or different implementations of the factory can create different types of objects.
- Observer: The Observer Pattern is a behavioral design pattern that establishes a one to many dependency between objects. When one object (the Subject) changes its state, all its dependent objects( the Observers) are notified and updated automatically. This pattern is widely used in event-driven systems.  
  The Key components of observer pattern are:
  1.  Subject (Publisher): The object being observer. it maintains a list of observers.
  2.  Observers (Subscribers): The objects that want to be notified of changes in the subject.
  3.  Loose Coupling: Observers are loosely couples to the subject, allowing for dynamic addition and removal of observers without affecting the subject.

# Observation:

We will build an application with users and publishers where, users can subscribe to messages sent by a publisher.

1. observation/AbstractObserver.java

```java

    package observation;

    import java.util.List;

    public interface AbstractObserver<T> {

        public List<T> getMessageList();

        public void task(T message);

    }

```

2. observation/Observee.java

```java

    package observation;

    import java.util.List;

    public interface Observee<T> {

        public List<AbstractObserver<T>> getObservers();

        public void addObserver(AbstractObserver<T> observer);

        public void notify(T message);

        public void removeObserver(AbstractObserver<T> observer);

    }

```

3. db/MemoryDb.java

```java

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

```

4. db/Publisher.java

```java

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

```

5. User.java

```java

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

```

6. menu/Input.java

```java

    package menu;

    import java.util.Scanner;

    public class Input {

        static Scanner scanner;

        private Input() {

        }

        private static void init() {

            scanner = new Scanner(System.in);

        }

        public static String readString(String message) {

            init();

            System.out.print(message + " ");

            return scanner.nextLine();

        }

        public static int readInt(String message) {

            init();

            System.out.print(message + " ");

            int v = scanner.nextInt();

            scanner.nextLine();

            return v;

        }

    }

```

7. menu/Menu.java

```java

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

```

8. App.java

```java

    import menu.Menu;

    class App {

        public static void main(String[] args) {

        Menu.showMenu();

        }

    }

```

# Conclusion:

We used factory, observer, singleton and strategy design patterns to build an
  simple application.
