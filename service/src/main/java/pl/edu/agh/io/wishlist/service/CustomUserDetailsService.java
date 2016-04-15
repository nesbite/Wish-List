package pl.edu.agh.io.wishlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.UserDetails;
import pl.edu.agh.io.wishlist.domain.UserSecurity;
import pl.edu.agh.io.wishlist.persistence.UserDetailsRepository;
import pl.edu.agh.io.wishlist.service.repository.UserDetailsService;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final UserDetailsService userService;

	@Autowired
	public CustomUserDetailsService(UserDetailsService userService) {
		this.userService = userService;
	}

	@Override
	public UserSecurity loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails user = userService.findByUsername(username);
		return new UserSecurity(user);
	}

}
