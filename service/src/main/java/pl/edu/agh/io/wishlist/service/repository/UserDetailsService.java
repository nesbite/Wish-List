package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.UserDetails;
import pl.edu.agh.io.wishlist.persistence.UserDetailsRepository;
import pl.edu.agh.io.wishlist.service.IUserDetailsService;

@Service
public class UserDetailsService implements IUserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserRepoService userRepoService;

    @Override
    public boolean registerUser(UserDetails userDetails) {
        if ((userDetailsRepository.findByUsername(userDetails.getUsername()) != null) || (userRepoService.getUser(userDetails.getUsername()) != null)) {
            return false;
        }
//        userDetails.addRole("USER");
        userDetailsRepository.save(userDetails);

//        userRepoService.addUser(new User(userDetails.getUsername()));
        return true;
    }

    @Override
    public boolean changePassword(String userId, String password) {
        UserDetails user = userDetailsRepository.findOne(userId);
        if(user == null){
            return false;
        }
        user.setPassword(password);
        userDetailsRepository.save(user);
        return true;
    }

    @Override
    public boolean changeEmail(String userId, String email) {
        UserDetails user = userDetailsRepository.findOne(userId);
        if(user == null){
            return false;
        }
        user.setEmail(email);
        userDetailsRepository.save(user);
        return true;
    }

    public UserDetails findByUsername(String username){
        return userDetailsRepository.findByUsername(username);
    }

    @Override
    public void save(UserDetails user) {
        userDetailsRepository.save(user);
    }

    public void deleteAll() {
        userDetailsRepository.deleteAll();
    }

}
