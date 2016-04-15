package pl.edu.agh.io.wishlist.domain;


import org.springframework.security.core.authority.AuthorityUtils;

public class UserSecurity  extends
		org.springframework.security.core.userdetails.User {
	private UserDetails userDetails;

	public UserSecurity(UserDetails userDetails) {
		super(userDetails.getUsername(), userDetails.getPassword(), AuthorityUtils
				.createAuthorityList(userDetails.getRole().toString()));
		this.userDetails = userDetails;
	}

	public UserDetails getUser() {
		return userDetails;
	}

	public String getId() {
		return userDetails.getId();
	}

	public String getRole() {
		return userDetails.getRole();
	}
}
