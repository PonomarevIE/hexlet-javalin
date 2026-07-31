package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        // Описываем, что загрузится по адресу /
        app.get("/", ctx -> ctx.result("Hello World"));

        app.get("/users", ctx -> ctx.result("GET /users"));
        app.get("users/{id}/post/{postId}", ctx -> {
            var userId =  ctx.pathParam("id");
            var postId =  ctx.pathParam("postId");
            ctx.result("GET User ID: " + userId + ", Post ID: " + postId);
        });

        app.get("/hello",
                ctx -> ctx.result("Hello, "+ctx.queryParamAsClass("name", String.class).getOrDefault("World")+"!"));

        app.post("/users", ctx -> ctx.result("POST /users"));
/*
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = new Course("Course " + id, "Course description " + id);
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });
*/
        app.get("/courses", ctx -> {
            List<Course> courses = List.of(new Course("Course 1", "Content 1"), new Course("Course 2", "Content 2"));
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.start(7070); // Стартуем веб-сервер
    }
}