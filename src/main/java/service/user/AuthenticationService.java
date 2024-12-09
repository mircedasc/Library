package service.user;

import model.User;
import model.validator.Notification;
import java.util.*;

public interface AuthenticationService {
    List<User> findAll();
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password);

    boolean delete(String username);

    boolean logout(User user);
}