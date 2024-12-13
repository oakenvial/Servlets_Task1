package ru.netology.repository;

import ru.netology.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class PostRepository {
    final private ConcurrentHashMap<Long, Post> posts;
    final private AtomicLong nextId;

    public PostRepository() {
        posts = new ConcurrentHashMap<>();
        nextId = new AtomicLong(1L);
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        if (posts.containsKey(id)) {
            return Optional.of(posts.get(id));
        } else {
            return Optional.empty();
        }
    }

    public void save(Post post) {
        final long postId = post.getId();
        if (postId == 0L) {
            post.setId(nextId.get());
            posts.put(post.getId(), post);
            nextId.incrementAndGet();
        } else if (posts.containsKey(postId)) {
            posts.put(postId, post);
        } else {
            posts.put(postId, post);
            nextId.set(Math.max(nextId.get(), postId + 1));
        }
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
