package cz.cvut.fel.jee.labEshop.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.jboss.solder.core.Veto;

@Veto
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;
	
	@Lob
	private byte[] image;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	



	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + Arrays.hashCode(image);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Image [description=" + description + ", id=" + id + "]";
	}
	
	

}
