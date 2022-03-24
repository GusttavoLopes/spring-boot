package com.gabrielle.passwordSafe.passwords;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gabrielle.passwordSafe.passwords.controllers.View;
import com.gabrielle.passwordSafe.users.User;


@Entity
@Table(name="passwords")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Password {
	
	@Id
    @Column(name = "pwd_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Password.class)
    private Integer id;

	@Column(name = "pwd_name")
	@JsonView(View.Password.class)
    private String name;
    
    @Column(name = "pwd_password")
	@JsonView(View.Password.class)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
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