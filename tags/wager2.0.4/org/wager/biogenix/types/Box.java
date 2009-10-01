package org.wager.biogenix.types;

// Generated 31/07/2007 14:27:55 by Hibernate Tools 3.1.0.beta5

import java.math.BigDecimal;

/**
 * IxInvBox generated by hbm2java
 */
public class Box implements java.io.Serializable {

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = -732580831028909478L;
	private BigDecimal boxkey;
	private String timestamp;
	private BigDecimal deleted;
	private String name;
	private BigDecimal available;
	private String description;
	private BigDecimal capacity;
	private BigDecimal tankkey;

	// Constructors

	/** default constructor */
	public Box() {
	}

	/** minimal constructor */
	public Box(BigDecimal boxkey, String name, BigDecimal tankkey) {
		this.boxkey = boxkey;
		this.name = name;
		this.tankkey = tankkey;
	}

	/** full constructor */
	public Box(BigDecimal boxkey, BigDecimal deleted, String name,
			BigDecimal available, String description, BigDecimal capacity,
			BigDecimal tankkey) {
		this.boxkey = boxkey;
		this.deleted = deleted;
		this.name = name;
		this.available = available;
		this.description = description;
		this.capacity = capacity;
		this.tankkey = tankkey;
	}

	// Property accessors
	public BigDecimal getBoxkey() {
		return this.boxkey;
	}

	public void setBoxkey(BigDecimal boxkey) {
		this.boxkey = boxkey;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getDeleted() {
		return this.deleted;
	}

	public void setDeleted(BigDecimal deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAvailable() {
		return this.available;
	}

	public void setAvailable(BigDecimal available) {
		this.available = available;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCapacity() {
		return this.capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getTankkey() {
		return this.tankkey;
	}

	public void setTankkey(BigDecimal tankkey) {
		this.tankkey = tankkey;
	}

}
