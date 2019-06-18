package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    //    UserRepository repository;
//    UserService service;
    @Autowired
    AdminRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        repository = new InMemoryUserRepositoryImpl();
//        service = new UserServiceImpl(new InMemoryUserRepositoryImpl());
        controller = new AdminRestController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.setAttribute("users", controller.getAll());
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String calories = request.getParameter("calories");
        User user = new User(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("user Name"),
                request.getParameter("email"),
                request.getParameter("password"),
                calories.isEmpty() ? 0 : Integer.valueOf(calories),
                Boolean.valueOf(request.getParameter("enabled")),
                EnumSet.of(Role.ROLE_ADMIN));

        log.info(user.isNew() ? "Create {}" : "Update {}", user);
//        repository.save(user);
        if (user.isNew()) {
            controller.create(user);
        } else {
            controller.update(user, Integer.valueOf(id));
        }
        response.sendRedirect("users");
//        super.doPost(request, response);
    }
}
