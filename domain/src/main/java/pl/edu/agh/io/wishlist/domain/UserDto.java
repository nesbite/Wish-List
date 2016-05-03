package pl.edu.agh.io.wishlist.domain;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pl.edu.agh.io.wishlist.domain.validation.PasswordMatches;
import pl.edu.agh.io.wishlist.domain.validation.ValidEmail;
import pl.edu.agh.io.wishlist.domain.validation.ValidPassword;

import static javax.swing.text.StyleConstants.Size;

@PasswordMatches
public class UserDto {
    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(final String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [firstName=").append(firstName).append("]").append("[lastName=").append(lastName).append("]").append("[email").append(email).append("]").append("[password").append(password).append("]");
        return builder.toString();
    }
}
