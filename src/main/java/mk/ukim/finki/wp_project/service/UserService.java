package mk.ukim.finki.wp_project.service;

import mk.ukim.finki.wp_project.model.User;

public interface UserService {
    User login(String username, String password);
    User register(String username, String password, String repeatPassword, String name, String surname, String country);
}