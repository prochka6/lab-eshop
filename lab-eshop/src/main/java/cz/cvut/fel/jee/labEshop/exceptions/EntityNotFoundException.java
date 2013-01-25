package cz.cvut.fel.jee.labEshop.exceptions;

/**
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Class<?> clazz;
	private Object identifier;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(Object clazz, Object identifier) {
		resolveEntityClassType(clazz);
		this.identifier = identifier;
	}

	public EntityNotFoundException(Object clazz, Object identifier, String message) {
		super(message);
		resolveEntityClassType(clazz);
		this.identifier = identifier;
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

	private void resolveEntityClassType(Object clazz) {
		if (clazz != null) {
			if (clazz instanceof Class) {
				this.clazz = (Class<?>) clazz;
			} else {
				this.clazz = clazz.getClass();
			}
		}
	}

	public Class<?> getEntityClass() {
		return clazz;
	}

	public Object getEntityIdentifier() {
		return identifier;
	}

}
