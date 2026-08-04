package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
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

        app.get(NamedRoutes.buildUserPath(), ctx -> {
            var page = new BuildUserPage();
            ctx.render("users/build.jte", model("page", page));
        });

        app.get(NamedRoutes.usersPath(), ctx -> {

            var term = ctx.queryParam("term");
            List<User> users = UserRepository.getEntities();
            List<User> foundUsers;

            if (term != null) {
                foundUsers = users.stream()
                        .filter(u -> StringUtils.startsWithIgnoreCase(u.getFirstName(), term))
                        .toList();
            } else {
                foundUsers = users;
            }
            var page = new UsersPage(foundUsers, term);
            ctx.render("users/index.jte", model("page", page));
        });

        app.post(NamedRoutes.usersPath(), ctx -> {
            try {
                var firstName = StringUtils.capitalize(ctx.formParam("firstName"));
                var lastName = StringUtils.capitalize(ctx.formParam("lastName"));
                var email = ctx.formParam("email").trim().toLowerCase();
                var password = ctx.formParamAsClass("password", String.class)
                                .check(value -> value.length() > 6, "Длина пароля должна быть больше 6")
                                .get();
                password = Security.encrypt(password);

                var user = new User(firstName, lastName, email, password);
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) {
                var firstName = ctx.formParam("firstName");
                var lastName = ctx.formParam("lastName");
                var email = ctx.formParam("email");
                var page = new BuildUserPage(firstName, lastName, email, e.getErrors());
                ctx.render("users/build.jte", model("page", page)).status(422);
            }
        });

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