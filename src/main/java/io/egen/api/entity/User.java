package io.egen.api.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
		{
			@NamedQuery(name="User.findAllUsers",query="SELECT u from User u ORDER BY u.firstName"),
			@NamedQuery(name="User.findByEmail",query="SELECT u FROM User u WHERE u.email=:email")
		}
		)
public class User {

  
	@Id
    private String id;
    private String firstName;
    private String lastName;
    
    @Column(unique = true)
    private String email;
    private String city;

    public User(){
    	this.id=UUID.randomUUID().toString();
    }
    
    public void setId(String id){
    	this.id=id;
    }

    public String getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString(){
		return "[id: "+id+"firstName : "+firstName+"lastName :"+lastName+"Email : "+email+"City : "+city+" ]";
    	
    }
    

}

