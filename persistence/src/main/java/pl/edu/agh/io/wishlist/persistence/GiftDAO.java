package pl.edu.agh.io.wishlist.persistence;

import pl.edu.agh.io.wishlist.domain.Gift;

import java.util.List;

public interface GiftDAO {
    Gift get(Long id);
    List<Gift> getAll(Long id);
    boolean insert(Long id, Gift gift);
    boolean remove(Long id);
    boolean update(Long id, String name, String description);
}
