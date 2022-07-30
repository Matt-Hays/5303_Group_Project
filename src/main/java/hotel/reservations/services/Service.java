/**
 * The Services Layer interface implemented by ServiceLayer.java.
 * Provides manipulation of data access objects to accomplish application requirements.
 *
 * @author Matthew Hays
 * @version 1.0
 *     - Initial draft 7/27/22 Matthew Hays
 */

package hotel.reservations.services;

import java.util.Set;
import java.util.UUID;

/**
 * Provides a base Service interface.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public interface Service<T, S> {
    T findById(S id);
    Set<T> findAll();
    T save(T object);
    T create(T object);
    void delete(T object);
}
