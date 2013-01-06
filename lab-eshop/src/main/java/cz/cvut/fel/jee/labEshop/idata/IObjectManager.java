package cz.cvut.fel.jee.labEshop.idata;

public interface IObjectManager{
	/**
     * This method persist object to DB
     * @param objectToAdd object which will be added to DB.
     */
    public void add(Object objectToAdd);
    /**
     * This method find in datebase object with type <code>classToFind</code> and
     * with identificator.
     * @param classToFind specify to find
     * @param id id of object to find
     * @return object type of <code>classToFind</code> with specify ID
     */
    public Object get(Class classToFind, int id);
    /**
     * This method remove object from database.
     * @param objectToRemove object to remove from database.
     */
    public void remove(Object objectToRemove);
    /**
     * This method update object in database.
     * @param objectToUpdate object to update in DB.
     */
    public void update(Object objectToUpdate);
    /**
     * This object refresh from actaully state in database.
     * @param objectToRefresh object which will be refreshed from actual database state.
     * @return refreshed object.
     */
    public Object refresh(Object objectToRefresh);
	
	
}
