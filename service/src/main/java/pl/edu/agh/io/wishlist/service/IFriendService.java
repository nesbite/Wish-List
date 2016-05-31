package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.User;

import java.util.List;

public interface IFriendService {
    List<User> getFriends(String id);

    boolean addFriend(String userId, String friendId);

    boolean deleteFriend(String userId, String friendId);

    List<User> getFriendsRequests(String id);
}