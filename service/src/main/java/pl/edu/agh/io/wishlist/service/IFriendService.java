package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.User;

import java.util.List;

public interface IFriendService {
    List<User> getFriends(Long id);

    boolean addFriend(Long userId, Long friendId);

    boolean deleteFriend(Long userId, Long friendId);
}
