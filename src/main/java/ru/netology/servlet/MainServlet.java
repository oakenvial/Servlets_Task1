package ru.netology.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;


public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();

            if (path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }

            if (path.matches("/api/posts/\\d+")) {
                final long postId = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(postId, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Error while handling GET request");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();

            if (path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
            }
        } catch (Exception e) {
            System.out.println("Error while handling POST request");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();

            if (path.matches("/api/posts/\\d+")) {
                final long postId = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(postId, resp);
            }
        } catch (Exception e) {
            System.out.println("Error while handling DELETE request");
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
