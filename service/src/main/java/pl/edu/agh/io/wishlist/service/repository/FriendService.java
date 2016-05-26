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
    public boolean addFriend(String username, String friendName) {

        User user = userRepository.findByUsername(username);
        if(user == null){
            return false;
        }
        List<String> friendIdList = user.getFriends();

        if (friendIdList.contains(friendName)) {
            return false;
        }

        friendIdList.add(friendName);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean deleteFriend(String username, String friendName) {


        User user = userRepository.findByUsername(username);
        if(user != null) {
            List<String> friendIdList = user.getFriends();

            if (!friendIdList.contains(friendName)) {
                return false;
            }

            friendIdList.remove(friendName);

            userRepository.save(user);
            return true;
        }
        return false;
    }
}