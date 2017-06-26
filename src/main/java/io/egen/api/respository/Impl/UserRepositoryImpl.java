package io.egen.api.respository.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import io.egen.api.entity.User;
import io.egen.api.respository.UserRepository;

@Repository

/*
* This layer is responsible for communication with the database either via your own JDBC implementation or
* Using SPRING data or anything else.
* */

public class UserRepositoryImpl implements UserRepository{
	
	@PersistenceContext
    EntityManager em;
    /*
    * Here we don't have to explicitly initialize EntityManager as
    * EntityManager em = emf.createEntityManager();
    * SPRING will do it for us.
    * */

	
	/*
	    * In the below method definitions you will notice that , we are not caring about the parameters that
	    * we are getting in the function .We are adding it to database without any validations,this is because
	    * in this application the Repository level gets the data or method call from the Service level and the
	    * service level is responsible to do the basic validation before sending data to Repository level which then
	    * makes call to database.
	    * This makes the Repository level LIGHT in operations.
	    * */

	    /*
	    * What about Transactions?
	    * Transaction were used while doing any write operation on the database.
	    *
	    * */
	
	
	@Override
    public List<User> findAll(){
		TypedQuery<User> query=em.createNamedQuery("User.findAllUsers", User.class);
		List<User> allUsersList=query.getResultList();
		if(!allUsersList.isEmpty()){
			return allUsersList;
		}else{
			return null;
		}
    }

    @Override
    public User findOne(String userId) {
    	return em.find(User.class,userId);
    }

    @Override
    public User create(User user) {
    	System.out.println("Printing User object in UserRepositoryImpl");
    	System.out.println(user.toString());
    	em.persist(user);
    	return user;
    }

    @Override
    public User update( User user) {
    	return em.merge(user);
    }

    @Override
    public void delete(User user){
    	em.remove(user);
    }

    @Override
    public User findByEmail(String email) {
    	TypedQuery<User> query=em.createNamedQuery("User.findByEmail",User.class);
    	query.setParameter("email",email);
    	List<User> userListByEmail=query.getResultList();
    	if(!userListByEmail.isEmpty()){
    		return userListByEmail.get(0);
    	}else{
    		return null;
    	}
    	
    }
}
