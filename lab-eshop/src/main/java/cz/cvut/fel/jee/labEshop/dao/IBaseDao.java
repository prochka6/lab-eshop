package cz.cvut.fel.jee.labEshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 * Base repository interface encapsulating work with database. Is heavily
 * influenced by JPA, which is used as persistence mechanism.
 * 
 * <p>
 * This interface should be used by all Dao classes as parent defining core
 * functionality for working with database.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 * 
 * @param <T>
 *            entity java class type repository works on.
 * @param <ID>
 *            entity id class
 */
public interface IBaseDao<T, ID> {

	/**
	 * @see EntityManager#getReference(Class, Object)
	 * @param id
	 *            primary key of entity
	 * @return the found entity instance
	 */
	T getReference(ID id);

	/**
	 * @see EntityManager#find(Class, Object)
	 * @param id
	 *            primary key of entity
	 * @return the found entity instance or null if the entity does not exist
	 */
	T get(ID id);

	/**
	 * Retrieves all entities from database. Should be used with caution !
	 * 
	 * @return all entities stored in db
	 */
	List<T> getAll();

	/**
	 * Persist new instance of the key hasn't been assigned yet or update entity
	 * instance. Mimics Hibernate saveOrUpdate method.
	 * 
	 * <p>
	 * Subclasses should override this method in case of composite primary keys
	 * or other primary key assign strategies.
	 * 
	 * @see EntityManager#merge(Object)
	 * @param entity
	 *            the entity instance
	 * @return the managed instance that the state was merged to
	 */
	T saveOrUpdate(T entity);

	/**
	 * Remove the entity instance.
	 * 
	 * @see EntityManager#remove(Object)
	 * @param entity
	 *            the entity
	 */
	void delete(T entity);

	/**
	 * Remove the entity instance by id.
	 * 
	 * @param id
	 *            the id of entity we want to remove.
	 * @throws EntityNotFoundException
	 *             if no such entity with given id exists.
	 */
	void deleteById(ID id) throws EntityNotFoundException;

	/**
	 * @see EntityManager#refresh(Object)
	 * @param entity
	 *            the entity
	 * @return refreshed entity instance
	 */
	T refresh(T entity);

	/**
	 * @see EntityManager#flush()
	 */
	void flush();

	/**
	 * @see EntityManager#flush()
	 * @see EntityManager#clear()
	 */
	void flushAndClear();

	/**
	 * Returns JPA {@linkplain EntityManager} which is used by this Dao.
	 * 
	 * @return the entityManager which is used
	 */
	EntityManager getEntityManager();

	/**
	 * Set {@linkplain EntityManager} which should be used in communication with
	 * database.
	 * 
	 * @param entityManager
	 *            the entityManager
	 */
	void setEntityManager(EntityManager entityManager);

}
