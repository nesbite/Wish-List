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
    public List<User> getFriends(String username) {
        User user;
        List<User> users = new ArrayList<>();

        if((user = userRepository.findByUsername(username)) != null) {
            List<String> friendIdList = user.getFriends();
            userRepository.findAll().forEach(a -> {
                if (friendIdList.contains(a.getUsername())){
                    users.add(a);
                }
            });
        }
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