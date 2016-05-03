package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IFriendService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService implements IFriendService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getFriends(String userId) {
        List<String> friendIdList = userRepository.findOne(userId).getFriends();

        List<User> users = new ArrayList<>();
        userRepository.findAll(friendIdList).forEach(users::add);
        return users;
    }

    @Override
    public boolean addFriend(String userId, String friendId) {
        if (!userRepository.exists(userId)) {
            return false;
        }

        User user = userRepository.findOne(userId);
        List<String> friendIdList = user.getFriends();

        if (friendIdList.contains(friendId)) {
            return false;
        }

        friendIdList.add(friendId);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean deleteFriend(String userId, String friendId) {
        if (!userRepository.exists(userId)) {
            return false;
        }

        User user = userRepository.findOne(userId);
        List<String> friendIdList = user.getFriends();

        if (!friendIdList.contains(friendId)) {
            return false;
        }

        friendIdList.remove(friendId);

        userRepository.save(user);

        return true;
    }
}