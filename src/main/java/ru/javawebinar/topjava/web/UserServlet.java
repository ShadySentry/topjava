package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepositoryImpl;

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
    UserRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryUserRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.setAttribute("users", repository.getAll());
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
        repository.save(user);
        response.sendRedirect("users");
//        super.doPost(request, response);
    }
}
