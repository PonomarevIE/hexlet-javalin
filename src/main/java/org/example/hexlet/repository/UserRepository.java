package org.example.hexlet.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.hexlet.Data;
import org.example.hexlet.model.User;

public class UserRepository {
    private static List<User> entities = Data.getUsers();

    public static void save(User user) {
        user.setId((long) entities.size() + 1);
        entities.add(user);
    }

    public static List<User> search(String term) {
        var users = entities.stream()
                .filter(entity -> entity.getFirstName().startsWith(term))
                .toList();
        return users;
    }

    public static Optional<User> find(Long id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findAny();
    }

    public static void delete(Long id) {

    }

    public static List<User> getEntities() {
        return entities;
    }

    public static void clear() {
        entities.clear();
    }
}
