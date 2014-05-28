package org.pikater.shared.database.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.pikater.shared.database.jpa.status.JPAUserStatus;

@Entity
@Table(name="_User")
@NamedQueries({
	@NamedQuery(name="User.getAll",query="select u from JPAUser u"),
	@NamedQuery(name="User.getByID",query="select u from JPAUser u where u.id=:id"),
	@NamedQuery(name="User.getByStatus",query="select u from JPAUser u where u.status=:status"),
	@NamedQuery(name="User.getByLogin",query="select u from JPAUser u where u.login=:login"),
	@NamedQuery(name="User.getByRole",query="select u from JPAUser u where u.role=:role")
})
public class JPAUser extends JPAAbstractEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	@Column(unique=true)
	private String login;
	@Column(nullable=false)
	private String password;
	private int priorityMax;
	private String email;
	@ManyToOne
	private JPARole role;
	@Enumerated(EnumType.STRING)
	private JPAUserStatus status;
	@OneToMany
	private List<JPABatch> batches;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	// TODO: eventually swap "private String password;" for "private String passwordHash;"
	
	/** Constructor for JPA Compatibility **/
	public JPAUser(){}
	
	/**
	 * Basic constructor for the JPAUser Entity. The status of the created user
	 * is ACTIVE, but in case of necessity it can be set to PASSIVE before the first persistance.
	 * The user tasks' maximal priority is set to 9.
	 * @param login The login for the user
	 */
	public JPAUser(String login,String password){
		this.login=login;
		this.password=password;
		this.created=new Date();
		this.priorityMax=9;
		this.status=JPAUserStatus.ACTIVE;
	}
	
	/**
	 * Constructor for the JPAUser Entity, where all fields can be set.
	 * @param login The login of the user.
	 * @param password The password of the user.
	 * @param role The role of the user.
	 * @param email The e-mail address of the user.
	 * @param maxPriority The maximal priority of user's tasks.
	 * @param status The user's account status.
	 */
	public JPAUser(String login,String password,JPARole role,String email,int maxPriority,JPAUserStatus status)
	{
		this(login,password);
		this.role=role;
		this.email=email;
		this.priorityMax=maxPriority;
		this.status=status;
	}
	
	public int getId() {
        return id;
    }
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPriorityMax() {
		return priorityMax;
	}
	public void setPriorityMax(int priorityMax) {
		this.priorityMax = priorityMax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public JPARole getRole() {
		return role;
	}
	public void setRole(JPARole role) {
		this.role = role;
	}
	public JPAUserStatus getStatus() {
		return status;
	}
	public void setStatus(JPAUserStatus status) {
		this.status = status;
	}
	public List<JPABatch> getBatches() {
		return batches;
	}
	public void setBatches(List<JPABatch> batches) {
		this.batches = batches;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	@Override
	public String getEntityName() {
		return "User";
	}
	@Override
	public void updateValues(JPAAbstractEntity newValues) throws Exception {
		JPAUser updateValues=(JPAUser)newValues;
		this.batches=updateValues.getBatches();
		this.email=updateValues.getEmail();
		this.login=updateValues.getLogin();
		this.password=updateValues.getPassword();
		this.priorityMax=updateValues.getPriorityMax();
		this.role=updateValues.getRole();
		this.status=updateValues.getStatus();
		this.created=updateValues.getCreated();
		this.lastLogin=updateValues.getLastLogin();
	}
	
}
