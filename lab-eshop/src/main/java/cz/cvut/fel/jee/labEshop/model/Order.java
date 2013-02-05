package cz.cvut.fel.jee.labEshop.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.jboss.solder.core.Veto;

@Veto
@Entity
@Table(name = "orderEshop")
public class Order extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	@OneToMany(mappedBy="order", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST })
	private List<OrderItem> items;
	private Long totalPrice;
	@Enumerated(EnumType.STRING)
	private State stateOfOrder;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateOfInsert;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PaymentMethod paymentMethod;
	
    public static enum State {
    	INSERTED,
        ACCEPTED,
        CLOSED,
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public Long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public State getStateOfOrder() {
		return stateOfOrder;
	}
	public void setStateOfOrder(State stateOfOrder) {
		this.stateOfOrder = stateOfOrder;
	}
	public Date getDateOfInsert() {
		return dateOfInsert;
	}
	public void setDateOfInsert(Date dateOfInsert) {
		this.dateOfInsert = dateOfInsert;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
