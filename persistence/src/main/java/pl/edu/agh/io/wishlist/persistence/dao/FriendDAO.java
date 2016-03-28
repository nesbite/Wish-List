package pl.edu.agh.io.wishlist.persistence.dao;

import pl.edu.agh.io.wishlist.domain.User;

import java.util.List;

public interface FriendDAO {
    List<User> getAll(Long id);
    boolean add(Long userId, Long friendId);
    boolean delete(Long userId, Long friendId);
}
