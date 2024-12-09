package view.model;

import javafx.beans.property.*;

public class UserDTO {
    private StringProperty username;

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public javafx.beans.property.StringProperty usernameProperty() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username");
        }

        return username;
    }

}
