/*
 * package com.gamboSupermarket.application.dto;
 * 
 * import java.sql.Timestamp; import java.util.List;
 * 
 * import javax.persistence.CascadeType; import javax.persistence.Column; import
 * javax.persistence.Entity; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.JoinColumn; import javax.persistence.OneToMany; import
 * javax.persistence.OneToOne; import javax.persistence.Table;
 * 
 * @Entity
 * 
 * @Table(name="customer") public class CustomerDetail extends BaseModel{
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY)
 * 
 * @Column(name="customer_id") private Long id;
 * 
 * @OneToOne(cascade = CascadeType.ALL)
 * 
 * @JoinColumn(name = "user_id", referencedColumnName = "id") private User user;
 * 
 * @Column(name="name") private String name;
 * 
 * @OneToMany(mappedBy = "customerDetail") private List<Address> address;
 * 
 * public Long getId() { return id; }
 * 
 * public void setId(Long id) { this.id = id; }
 * 
 * 
 * public User getUser() { return user; }
 * 
 * public void setUser(User user) { this.user = user; }
 * 
 * 
 * 
 * }
 */