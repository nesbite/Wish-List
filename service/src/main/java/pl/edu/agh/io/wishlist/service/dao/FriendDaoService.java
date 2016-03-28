package pl.edu.agh.io.wishlist.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.dao.FriendDAO;
import pl.edu.agh.io.wishlist.service.IFriendService;

import java.util.List;

@Service("friendDaoService")
public class FriendDaoService implements IFriendService {
    @Autowired
    FriendDAO friendDAO;

    @Override
    public List<User> getFriends(Long id) {
        return friendDAO.getAll(id);
    }

    @Override
    public boolean addFriend(Long userId, Long friendId) {
        return friendDAO.add(userId, friendId);
    }

    @Override
    public boolean deleteFriend(Long userId, Long friendId) {
        return friendDAO.delete(userId, friendId);
    }
}
