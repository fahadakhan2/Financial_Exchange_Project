package trading.sim;

import product.book.DataValidationException;
import product.book.OrderDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public final class UserManager {
    private static UserManager instance;
    private static final HashMap<String, User> users = new HashMap<>();

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void init(String[] usersIn) throws DataValidationException {
        for (String userId : usersIn) {
            User newUserObject = new User(userId);
            users.put(userId, newUserObject);
        }
    }

    public User getRandomUser() {
        if (users.isEmpty()) {
            return null;
        }
        ArrayList<User> userObjectsList = new ArrayList<>(users.values());

        Random random = new Random();
        int randomIndex = random.nextInt(userObjectsList.size());

        return userObjectsList.get(randomIndex);
    }

    public void addToUser(String userId, OrderDTO o) throws DataValidationException {
        User user = users.get(userId);
        user.addOrder(o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (User u : users.values()) {
            sb.append(u.toString());
        }
        return sb.toString();
    }

    public User getUser(String id) {
        return users.get(id);
    }
}
