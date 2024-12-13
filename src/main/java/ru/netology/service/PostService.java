package ru.netology.service;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import java.util.List;


public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public String save(Post post) {
        repository.save(post);
        return "Post saved successfully";
    }

    public String removeById(long id) {
        repository.removeById(id);
        return "Post deleted successfully";
    }
}
