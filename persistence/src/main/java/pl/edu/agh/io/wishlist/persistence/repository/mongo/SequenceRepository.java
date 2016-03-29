package pl.edu.agh.io.wishlist.persistence.repository.mongo;

import pl.edu.agh.io.wishlist.domain.exception.SequenceException;

public interface SequenceRepository {

    long getNextSequenceId(String key) throws SequenceException;

}
