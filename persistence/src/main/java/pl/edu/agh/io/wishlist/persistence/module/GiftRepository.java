package pl.edu.agh.io.wishlist.persistence.module;

import pl.edu.agh.io.wishlist.domain.module.Gift;

public interface GiftRepository {

    void save(Gift gift);

    boolean exists(String id);

    int count();

    void delete(String id);

    Gift find(String id);
}
