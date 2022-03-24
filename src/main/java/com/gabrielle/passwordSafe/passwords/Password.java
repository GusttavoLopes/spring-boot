package com.gabrielle.passwordSafe.passwords;

import javax.persistence.*;

import com.gabrielle.passwordSafe.users.User;


@Entity
@Table(name="password")
public class Password {
	
	@Id
    @Column(name = "pwd_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "pwd_name")
    private String name;
    
    @Column(name = "pwd_password")
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "usr_id")
    private User user;
    
    
    public Password() {}
    
    public Password(String name,String  password, User user) {
        this.name = name;
        this.password = password;
        this.user = user;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isValid() {
        return name != null && password != null;
    }
    	
}