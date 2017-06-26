package io.egen.api.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.api.constants.URI;
import io.egen.api.entity.User;
import io.egen.api.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

/*
* Controllers are used for Mapping functions to HTTP end points.
* The only responsibility of a controller to Map a function to an HTTP end point(Like PUT,POST,GET,DELETE),
* setting the response body of the request,response code and nothing else.
* Controller SHOULD NOT HAVE LOGIC like From where do I get this response?Do I talk to database?
* what kind of database should I talk to?
* The lower level layers will take care of how to prepare data and they will send it to controller.
* */


//@Controller
/*
* It is similar to @Component notation of SPRING.
* @Controller annotation is just used to denote more specific @Component which tell SPRING that
* this is a Component but it acts as a Controller layer for this application.
* */


//@ResponseBody //Since all of the methods of the class are using this annotation , @ResponseBody can be declared at
              //class level


@RestController

/*
* @RestController is a SPRING's way of reducing the number of annotation used in SPRING app
* @RestController = @Controller + @ResponseBody
* So if you just declare @RestController,you need not write @Controller and  @ResponseBody
* */

@RequestMapping(value = URI.USERS)
//@RequestMapping(value = "/users")
/* Since all of the API values are starting with "/users" , we can declare it here and will only
 * put values which are coming after "/users" string in the actual 'value' of the @RequestMapping at method level
 * */

//@Api(tags="UserC")

public class UserController {


    private UserService service;

    public UserController(UserService service){
        this.service=service;
    }

    /*
    * Since we are following REST pattern and will not be implementing any VIEW (only MC)
    * by using @ResponseBody annotation we are telling the SPRING that whatever these functions returns
    * add it to a response body in the form of String.
    * */
    //@ResponseBody //No need to declared it here since it has already been declared at the class level


    /*
    * Before setting ResponseBody,we need to map this function with the HTTP Request which will be used
    * to find all Users.
    * This is basically a MAPPING OF FUNCTION TO REQUEST,this allows SPRING to create a mapping when it Boots.
    * */
    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@ApiOperation(value = "Find all Users",notes="This operation returns a List<User> of all the users from the database")
    public List<User> findAll(){
        System.out.println("Reached findAll() in UserController.java");
        return service.findAll();
    }


    //@ResponseBody //No need to declared it here since it has already been declared at the class level
    @RequestMapping(method = RequestMethod.GET,
                    //value = "/users/{id}",
                    //value = "{id}",
                    value = URI.ID,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@ApiOperation(value = "Find User by Id",notes="This operation returns a User by ID from the database")
    /*
    * @PathVariable("id") annotation is used to fetch the 'id' that is received through the GET request URL
    * the interpretation is that 'You will be receiving an 'id' parameter as a path variable,try to convert this
    * path variable onto String and assign it to the local variable called is 'userId''
    * */
    public User findOne(@PathVariable("id") String userId){
        return service.findOne(userId);
    }


    //@ResponseBody //No need to declared it here since it has already been declared at the class level
    @RequestMapping(method = RequestMethod.POST,
                    //value = "/users/",
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    /*
    * @RequestBody annotation is used here to notify SPRING that since it is a POST httpRequest the request itself will
    * have a JSON into it.
    * Try to convert this JSON request body into a User Object and assign it to local object variable 'user'
    * */
    public User create(@RequestBody User user){
        return service.create(user);
    }


    //@ResponseBody //No need to declared it here since it has already been declared at the class level
    @RequestMapping(method = RequestMethod.PUT,
                    //value = "/users/{id}",
                    //value = "{id}",
                    value = URI.ID,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public User update(@PathVariable("id") String userId, @RequestBody User user){
        return service.update(userId,user);
    }


    //@ResponseBody //No need to declared it here since it has already been declared at the class level
    @RequestMapping(method = RequestMethod.DELETE,
                    //value = "/users/{id}"
                    //value = "{id}"
                    value = URI.ID)
    /* @ResponseBody on 'void' return type is optional */
    public void delete(@PathVariable("id") String userId){
        service.delete(userId);
    }

}

