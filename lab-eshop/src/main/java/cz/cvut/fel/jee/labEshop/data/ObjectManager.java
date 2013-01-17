package cz.cvut.fel.jee.labEshop.data;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.cvut.fel.jee.labEshop.idata.IObjectManager;

/**
 * This class is ancestor of all managers, it provide acces to EntityManager and
 * implements all basic operation with persiting object.
 */
public class ObjectManager implements IObjectManager, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected EntityManager em;

	@Override
	public void add(Object objectToAdd) {
		objectToAdd = em.merge(objectToAdd);
		em.persist(objectToAdd);
	}

	@Override
	public Object get(Class classToFind, int id) {
		Object o = em.find(classToFind, id);
		return o;
	}

	@Override
	public void remove(Object objectToRemove) {
		objectToRemove = em.merge(objectToRemove);
		em.remove(objectToRemove);

	}

	@Override
	public void update(Object objectToUpdate) {
		objectToUpdate = em.merge(objectToUpdate);
		em.persist(objectToUpdate);
	}

	@Override
	public Object refresh(Object objectToRefresh) {
		objectToRefresh = em.merge(objectToRefresh);
		em.refresh(objectToRefresh);
		return objectToRefresh;
	}

}
