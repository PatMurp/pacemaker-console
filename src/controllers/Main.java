package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;

import com.google.common.base.Optional;

import models.Activity;
import models.User;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;


public class Main
{
  public PacemakerAPI paceApi;
  
  /**
   * Constructor to load from database
   * @throws Exception
   */
  public Main() throws Exception
  {
//    File datastore = new File("datastore.xml");
//    Serializer serializer  = new XMLSerializer(datastore);
    File datastore = new File("datastore.JSON");
    Serializer serializer = new JSONSerializer(datastore);
    
    paceApi = new PacemakerAPI(serializer);
    if (datastore.isFile())
    {
      paceApi.load();
    }
  }

	/**
	 * Command to create a new user
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 */
	@Command(description="Create a new User")
	  public void createUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName, 
	                          @Param(name="email")      String email,     @Param(name="password")  String password)
	  {
		paceApi.createUser(firstName, lastName, email, password);
	  }
	
	  /**
	   * Command to list a user based on email
	   * @param email
	   */
	  @Command(description="Get a Users details")
	  public void listUser (@Param(name="email") String email)
	  {
		  User user = paceApi.getUserByEmail(email);
		  System.out.println(user);
	  }
	  
	  /**
	   * Command to list a user based on id
	   * @param id
	   */
	  @Command(description="Get a Users details")
	  public void listUser(@Param(name="id")Long id)
	  {
	    User user = paceApi.getUser(id);
	    System.out.println(user);
	  }
	
	  /**
	   * Command to list all users
	   */
	  @Command(description="Get all users details")
	  public void listUsers ()
	  {
		  Collection<User> users = paceApi.getUsers();
		  System.out.println(users);
		  
		  
	  }
	
	  /**
	   * Command to delete a user using id
	   * @param id
	   */
	  @Command(description="Delete a User")
	  public void deleteUser (@Param(name="id") Long id)
	  {
		  Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
		  if (user.isPresent())
		  {
			  paceApi.deleteUser(user.get().id);
		  }
		  
	  }
	  
	 
	  /**
	   * Command to add an activity to a user using the users id
	   * @param id
	   * @param type
	   * @param location
	   * @param distance
	   * @param starttime
	   * @param duration
	   */
	  @Command(description="Add an activity")
	  public void addActivity (@Param(name="user-id")  Long   id,       @Param(name="type") String type, 
	                           @Param(name="location") String location, @Param(name="distance") double distance,
	                           @Param(name="start time") String starttime, @Param(name="duration") String duration)
	  {
	    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
	    if (user.isPresent())
	    {
	      paceApi.createActivity(id, type, location, distance, starttime, duration);
	    }
	  }
	  
	  /**
	   * Command to add a location to an activity using the activities id
	   * @param id
	   * @param latitude
	   * @param longitude
	   */
	  @Command(description="Add Location to activiy")
	  public void addLocation (@Param(name="activity-id") Long id, @Param(name="latitude") float latitude,
			  @Param(name="longitude") float longitude)
			  {
		  		Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(id));
		  		if (activity.isPresent())
		  		{
		  			paceApi.addLocation(activity.get().id, latitude, longitude);
		  		}
			  }
	  
	  /**
     * Command to list a users activities using the users id
     * @param id
     */
    @Command(description="Get a users activities") 
    public void listActivities(@Param(name="user id")Long id)
    {
      Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
      if (user.isPresent())
      {
        Map<Long, Activity> activ = paceApi.getUserActivities(user.get().id);
        System.out.println(activ);
      }
      else
      {
        System.out.println("!!! The user: " + id + " does not exist. Please enter a valid user ID !!!");
      }
	  }
    
    /**
     * Command to sort user activities by parameters
     * type, location, distance, date, & duration
     * @param id
     * @param parameter
     */
    @Command(description="Sort a users activities by parameters")
    public void listActivities(@Param(name="userid")Long id, 
        @Param(name="sortBy: type, location, distance, date, duration")String parameter)
    {
      Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
      if (user.isPresent())
      {
        Map<Long, Activity> userActiv = paceApi.getUserActivities(user.get().id);
        List<Activity> userActivList = new ArrayList<>(userActiv.values());
        
        switch(parameter)
        {
        case "type":
          Collections.sort(userActivList, Activity.compareType);
          System.out.println(userActivList);
          break;
        case "location":
          Collections.sort(userActivList, Activity.compareLocation);
          System.out.println(userActivList);
          break;
        case "distance":
          Collections.sort(userActivList, Activity.compareDistance);
          System.out.println(userActivList);
          break;
        case "date":
          Collections.sort(userActivList, Activity.compareDate);
          System.out.println(userActivList);
          break;
        case "duration":
          Collections.sort(userActivList, Activity.compareDuration);
          System.out.println(userActivList);
          break;
        default:
          System.out.println("Invalid parameter: " + parameter + " Please enter valid parameter");
          break;
        }
      }
      else
      {
        System.out.println("!!! The user: " + id + " does not exist. Please enter a valid user ID !!!");
      }
    }
	  
	  /**
	   * Command to read from persistent store(all users + activities)
	   * @throws Exception
	   */
	  @Command(description="Load all users + activities")
	  public void load() throws Exception
	  {
	    paceApi.load();
	  }
	  
	  /**
	   * Command to write to persistent store
	   * @throws Exception
	   */
	  @Command(description="Store all users + activities")
	  public void store() throws Exception
	  {
	    paceApi.store();
	  }

  /**
   * Create a command line interface using cliche library
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception
  {
    Main main = new Main();
    
    Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pcemaker-console - ?help for instructions", main);
    shell.commandLoop(); 
    
    main.paceApi.store();
  }
}
