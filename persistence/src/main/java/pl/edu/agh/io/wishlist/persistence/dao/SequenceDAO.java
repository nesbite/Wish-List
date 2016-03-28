package pl.edu.agh.io.wishlist.persistence.dao;

import pl.edu.agh.io.wishlist.domain.exception.SequenceException;

public interface SequenceDAO {

    long getNextSequenceId(String key) throws SequenceException;

}
