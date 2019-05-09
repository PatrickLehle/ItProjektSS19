package de.hdm.itprojektss19.team03.scart.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class BusinessObject implements Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;
	protected int id = 0;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " #" + this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) o;
			try {
				if (bo.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

}
