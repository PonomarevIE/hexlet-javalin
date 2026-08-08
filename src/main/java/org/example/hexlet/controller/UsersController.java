package org.example.hexlet.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.example.hexlet.NamedRoutes;
//import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.dto.users.UsersPage;
import org.example.hexlet.dto.users.UserPage;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.hexlet.util.Security;

import java.util.List;

public class UsersController {
    public static void index(Context ctx) {
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
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
        var page = new UserPage(user);
        ctx.render("users/show.jte", model("page", page));
    }

    public static void build(Context ctx) {
        var page = new BuildUserPage();
        ctx.render("users/build.jte", model("page", page));
    }

    public static void create(Context ctx) {
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
    }

    public static void edit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
        var page = new UserPage(user);
        ctx.render("users/edit.jte", model("page", page));
    }


    public static void update(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();

        var firstName = StringUtils.capitalize(ctx.formParam("firstName"));
        var lastName = StringUtils.capitalize(ctx.formParam("lastName"));
        var email = ctx.formParam("email").trim().toLowerCase();;

        var user = UserRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("User with id = " + id + " not found"));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        UserRepository.save(user);
        ctx.redirect(NamedRoutes.usersPath());
    }

    public static void destroy(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        UserRepository.delete(id);
        ctx.redirect(NamedRoutes.usersPath());
    }

}