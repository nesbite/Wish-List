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
        User friend = userRepository.findByUsername(friendName);
        if(user == null || friend == null){
            return false;
        }
        List<String> userFriendList = user.getFriends();
        List<String> userFriendsRequestsList = user.getFriendsRequests();

        List<String> friendFriendList = friend.getFriends();
        List<String> friendFriendsRequestsList = friend.getFriendsRequests();

        if(userFriendList.contains(friendName) || friendFriendList.contains(username)){
            return false;
        }
        if (userFriendsRequestsList.contains(friendName)) {
            userFriendsRequestsList.remove(friendName);
            userFriendList.add(friendName);
            friendFriendList.add(username);
            userRepository.save(user);
            userRepository.save(friend);
        } else {
            friendFriendsRequestsList.add(username);
            userRepository.save(friend);
        }

        return true;
    }

    @Override
    public boolean deleteFriend(String username, String friendName) {


        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendName);
        if(user != null && friend != null) {
            List<String> userFriendList = user.getFriends();
            List<String> friendFriendList = friend.getFriends();

            if (!userFriendList.contains(friendName) || !friendFriendList.contains(username)) {
                return false;
            }

            userFriendList.remove(friendName);
            friendFriendList.remove(username);
            userRepository.save(user);
            userRepository.save(friend);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getFriendsRequests(String username) {
        User user;
        List<User> users = new ArrayList<>();

        if((user = userRepository.findByUsername(username)) != null) {
            List<String> friendIdList = user.getFriendsRequests();
            userRepository.findAll().forEach(a -> {
                if (friendIdList.contains(a.getUsername())){
                    users.add(a);
                }
            });
        }
        return users;
    }
}