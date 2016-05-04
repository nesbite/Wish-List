package pl.edu.agh.io.wishlist.android.domain;

import java.util.Collection;


public class Privilege {

    private String id;

    private String name;

    private Collection<Role> roles;

    public Privilege() {}

    public Privilege(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Privilege privilege = (Privilege) obj;
        if (!privilege.equals(privilege.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
        return builder.toString();
    }
}
