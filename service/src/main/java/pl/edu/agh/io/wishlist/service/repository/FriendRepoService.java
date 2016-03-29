package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;
import pl.edu.agh.io.wishlist.service.IFriendService;

import java.util.ArrayList;
import java.util.List;


public class FriendRepoService implements IFriendService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getFriends(Long userId) {
        List<Long> friendIdList = userRepository.findOne(userId).getFriends();

        List<User> users = new ArrayList<>();
        userRepository.findAll(friendIdList).forEach(users::add);
        return users;
    }

    @Override
    public boolean addFriend(Long userId, Long friendId) {
        if (!userRepository.exists(userId)) {
            return false;
        }

        User user = userRepository.findOne(userId);
        List<Long> friendIdList = user.getFriends();

        if (friendIdList.contains(friendId)) {
            return false;
        }

        friendIdList.add(friendId);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean deleteFriend(Long userId, Long friendId) {
        if (!userRepository.exists(userId)) {
            return false;
        }

        User user = userRepository.findOne(userId);
        List<Long> friendIdList = user.getFriends();

        if (!friendIdList.contains(friendId)) {
            return false;
        }

        friendIdList.remove(friendId);

        userRepository.save(user);

        return true;
    }
}
