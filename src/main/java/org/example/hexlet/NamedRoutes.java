package org.example.hexlet;

public class NamedRoutes {

    // Маршрут пользователей
    public static String usersPath() {
        return "/users";
    }

    // Это нужно, чтобы не преобразовывать типы снаружи
    public static String userPath(Long id) {
        return userPath(String.valueOf(id));
    }

    public static String userPath(String id) {
        return "/user/" + id;
    }

    public static String editUserPath(Long id) {
        return editUserPath(String.valueOf(id));
    }

    public static String editUserPath(String id) {
        return "/user/" + id + "/edit";
    }

    // Маршрут добавления пользователя
    public static String buildUserPath() {
        return "/users/build";
    }

    // Маршрут курсов
    public static String coursesPath() {
        return "/courses";
    }

    // Это нужно, чтобы не преобразовывать типы снаружи
    public static String coursePath(Long id) {
        return coursePath(String.valueOf(id));
    }

    public static String coursePath(String id) {
        return "/courses/" + id;
    }
}