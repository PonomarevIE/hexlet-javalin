package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.User;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.util.Security;

public class HelloWorld {
    private static final List<User> USERS = Data.getUsers();

    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        // Описываем, что загрузится по адресу /


        app.get("users/{id}/post/{postId}", ctx -> {
            var userId =  ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("GET User ID: " + userId + ", Post ID: " + postId);
        });

        // строка адреса для проверки XSS-атаки  "http://localhost:7070/users/%3Cscript%3Ealert('attack!')%3B%3C%2Fscript%3E"
        app.get("/users/attack/{id}", ctx -> {
            /*
            // уязвимый код
            var id = ctx.pathParam("id");
            ctx.contentType("html");
            ctx.result("<h1>" + id + "</h1>");
             */
            // render выполняет устранение уязвимости
            var id = ctx.pathParam("id");
            var user = new User(id, "", "", "");
            user.setId(1L);

            ctx.render("user.jte", model("user", user));
        });

        app.get(NamedRoutes.userPath("{id}"), UsersController::show);

        app.get(NamedRoutes.buildUserPath(), UsersController::build);

        app.get(NamedRoutes.usersPath(), UsersController::index);

        app.post(NamedRoutes.usersPath(), UsersController::create);

        app.delete(NamedRoutes.userPath("{id}"), UsersController::destroy); // !!! На страницах нет отправки такого запроса

        app.get(NamedRoutes.editUserPath("{id}"), UsersController::edit);

        app.post(NamedRoutes.editUserPath("{id}"), UsersController::update); // по классике должен быть метод patch - типа так app.patch("/users/{id}", UsersController::update); Но patch нельзя вызвать без JS

        app.get("/hello",
                ctx -> ctx.result("Hello, "+ctx.queryParamAsClass("name", String.class).getOrDefault("World")+"!"));

         app.get(NamedRoutes.coursePath("{id}"), ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var course = new Course(id, "Course " + id, "Course description " + id);
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get(NamedRoutes.coursesPath(), ctx -> {
            List<Course> courses = List.of(new Course(1L, "Course 1", "Content 1"), new Course(2L, "Course 2", "Content 2"));
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get("/", ctx -> ctx.render("index.jte"));

        app.start(7070); // Стартуем веб-сервер
    }
}