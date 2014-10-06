package controllers;

import java.util.*;

import utils.Serializer;

import com.google.common.base.Optional;
import models.Activity;
import models.Location;
import models.User;



public class PacemakerAPI
{
  private Serializer serializer;
  
  private Map<Long, User> userIndex = new HashMap<>();
  private Map<String, User> emailIndex = new HashMap<>();
  private Map<Long, Activity> activitiesIndex = new HashMap<>();
  
 
  /**
   * PacemakerAPI serializer Constructor
   * @param serializer
   */
  public PacemakerAPI(Serializer serializer)
  {
    this.serializer = serializer;
  }

 
  /**
   * Load the users, email, and activities data
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public void load() throws Exception
  {
    serializer.read();
    activitiesIndex   = (Map<Long, Activity>) serializer.pop();
    emailIndex        =(Map<String, User>)    serializer.pop();
    userIndex         =(Map<Long, User>)      serializer.pop();
  }
  
  /**
   * Store the users, email, and activities data
   * @throws Exception
   */
  void store() throws Exception
  {
    serializer.push(userIndex);
    serializer.push(emailIndex);
    serializer.push(activitiesIndex);
    serializer.write();
  }
  
  /**
   * @return all user details
   */
  public Collection<User> getUsers()
  {
    return userIndex.values();
  }

  
  /**
   * delete user data from user and email maps
   */
  public void deleteUsers()
  {
    userIndex.clear();
    emailIndex.clear();
  }

  /**
   * Create a new user and add data to the user and email maps
   * @param firstName
   * @param lastName
   * @param email
   * @param password
   * @return User details
   */
  public User createUser(String firstName, String lastName, String email, String password)
  {
    User user = new User(firstName, lastName, email, password);
    userIndex.put(user.id, user);
    emailIndex.put(email, user);
    return user;
  }

  /**
   * Get a user details using users email address
   * @param email
   * @return user details
   */
  public User getUserByEmail(String email)
  {
    return emailIndex.get(email);
  }

  /**
   * Get a user details using the users id
   * @param id
   * @return user details
   */
  public User getUser(Long id)
  {
    return userIndex.get(id);
  }

  /**
   * @param id
   */
  public void deleteUser(Long id)
  {
    User user = userIndex.remove(id);
    emailIndex.remove(user.email);
  }

  public void createActivity(Long id, String type, String location, double distance)
  {
    Activity activity = new Activity(type, location, distance);
    Optional<User> user = Optional.fromNullable(userIndex.get(id)); 
    if (user.isPresent())
    {
      user.get().activities.put(activity.id, activity);
      activitiesIndex.put(activity.id, activity);
    }
  }

  public Activity getActivity(Long id)
  {
    return activitiesIndex.get(id);
  }

  public void addLocation(Long id, float latitude, float longitude)
  {
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent())
    {
      activity.get().route.add(new Location(latitude, longitude));
    }
  }
}
