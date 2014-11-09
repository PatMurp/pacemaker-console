package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
   * Constructor to load from persistent store
   * 
   * @throws Exception
   */
  public Main() throws Exception
  {
    File datastore = new File("datastore.xml");
    Serializer serializer = new XMLSerializer(datastore);

    paceApi = new PacemakerAPI(serializer);
    if (datastore.isFile())
    {
      load();
    }
  }

  /**
   * Method to set serializer to use XML format
   * 
   * @throws Exception
   */
  public void XMLFile() throws Exception
  {
    File datastore = new File("datastore.xml");
    Serializer serializer = new XMLSerializer(datastore);

    paceApi = new PacemakerAPI(serializer);
    if (datastore.isFile())
    {
      load();
    }
  }

  /**
   * Method to set serializer to use JSON format
   * 
   * @throws Exception
   */
  public void JSONFile() throws Exception
  {
    File datastore = new File("datastore.JSON");
    Serializer serializer = new JSONSerializer(datastore);

    paceApi = new PacemakerAPI(serializer);
    if (datastore.isFile())
    {
      load();
    }
  }

  /**
   * Command to list all users
   */
  @Command(description = "Get all users details")
  public void listUsers()
  {
    List<User> allUsers = new ArrayList<>(paceApi.getUsers());

    if (allUsers.size() > 0)
    {
      System.out.println("ok");
      User.userTable(allUsers);
    }
    else
    {
      System.out.println();
      System.out.println(allUsers);
      System.out.println();
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
  @Command(description = "Create a new User")
  public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
      @Param(name = "email") String email, @Param(name = "password") String password)
  {
    paceApi.createUser(firstName, lastName, email, password);

    System.out.println("ok");
    List<User> newUser = Arrays.asList(paceApi.getUserByEmail(email));
    User.userTable(newUser);
  }

 

  /**
   * Command to list a user based on email
   * 
   * @param email
   */
  @Command(description = "Get a Users details")
  public void listUser(@Param(name = "email") String email)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (user.isPresent())
    {
      System.out.println("ok");
      List<User> userEmail = Arrays.asList(paceApi.getUserByEmail(email));
      User.userTable(userEmail);
    }
    else
    {
      System.out.println();
      System.out.println("Invalid email : " + email + " Please enter a valid email");
      System.out.println();
    }
  }

  /**
   * Command to list a user based on id
   * 
   * @param id
   */
  @Command(description = "Get a Users details")
  public void listUser(@Param(name = "id") Long id)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
    if (user.isPresent())
    {
      System.out.println("ok");
      List<User> userId = Arrays.asList(paceApi.getUser(id));
      User.userTable(userId);
    }
    else
    {
      System.out.println();
      System.out.println("The user: " + id + " does not exist. Please enter a valid id");
      System.out.println();
    }
  }
  
  /**
   * Command to sort user activities by parameters type, location, distance,
   * date, & duration
   * 
   * @param id
   * @param parameter
   */
  @Command(description = "Sort a users activities by parameters")
  public void listActivities(@Param(name = "userid") Long id,
      @Param(name = "sortBy: type, location, distance, date, duration") String parameter)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
    if (user.isPresent())
    {
      Map<Long, Activity> userActiv = paceApi.getUserActivities(user.get().id);
      List<Activity> userActivList = new ArrayList<>(userActiv.values());
      System.out.println("ok");
      if (userActivList.size() > 0)
      {
        switch (parameter)
        {
        case "type":
          Collections.sort(userActivList, Activity.compareType);
          Activity.activityTable(userActivList);
          break;
        case "location":
          Collections.sort(userActivList, Activity.compareLocation);
          Activity.activityTable(userActivList);
          break;
        case "distance":
          Collections.sort(userActivList, Activity.compareDistance);
          Activity.activityTable(userActivList);
          break;
        case "date":
          Collections.sort(userActivList, Activity.compareDate);
          Activity.activityTable(userActivList);
          break;
        case "duration":
          Collections.sort(userActivList, Activity.compareDuration);
          Activity.activityTable(userActivList);
          break;
        default:
          System.out.println();
          System.out.println("Invalid parameter: " + parameter + " Please enter valid parameter");
          System.out.println();
          break;
        }
      }
      else
      {
        System.out.println();
        System.out.println(userActivList);
        System.out.println();
      }
    }
    else
    {
      System.out.println();
      System.out.println("!!! The user: " + id + " does not exist. Please enter a valid user ID !!!");
      System.out.println();
    }
  }
  
  /**
   * Command to list a users activities using the users id
   * 
   * @param id
   */
  @Command(description = "Get a users activities")
  public void listActivities(@Param(name = "user id") Long id)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
    if (user.isPresent())
    {
      System.out.println("ok");
      Map<Long, Activity> activ = paceApi.getUserActivities(user.get().id);
      List<Activity> usrActiv = new ArrayList<>(activ.values());
      if (usrActiv.size() > 0)
      {
        Activity.activityTable(usrActiv);
      }
      else
      {
        System.out.println();
        System.out.println(usrActiv);
        System.out.println();
      }
    }
    else
    {
      System.out.println();
      System.out.println("The user: " + id + " does not exist. Please enter a valid user ID");
      System.out.println();
    }
  }

  /**
   * Command to delete a user using id
   * 
   * @param id
   */
  @Command(description = "Delete a User")
  public void deleteUser(@Param(name = "id") Long id)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
    if (user.isPresent())
    {
      paceApi.deleteUser(user.get().id);
      System.out.println("ok");
      System.out.println();
    }
  }

  /**
   * Command to add an activity to a user using the users id
   * 
   * @param id
   * @param type
   * @param location
   * @param distance
   * @param starttime
   * @param duration
   */
  @Command(description = "Add an activity")
  public void addActivity(@Param(name = "user-id") Long id, @Param(name = "type") String type,
      @Param(name = "location") String location, @Param(name = "distance") double distance,
      @Param(name = "start time") String starttime, @Param(name = "duration") String duration)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUser(id));
    if (user.isPresent())
    {
      paceApi.createActivity(id, type, location, distance, starttime, duration);
      System.out.println("ok");

      List<Activity> newActivitiy = Arrays.asList(paceApi.getActivity(id));
      Activity.activityTable(newActivitiy);
    }
    else
    {
      System.out.println();
      System.out.println("The user: " + id + " does not exist. Please enter a valid id");
      System.out.println();
    }
  }

  /**
   * Command to add a location to an activity using the activities id
   * 
   * @param id
   * @param latitude
   * @param longitude
   */
  @Command(description = "Add Location to activiy")
  public void addLocation(@Param(name = "activity-id") Long id, @Param(name = "latitude") float latitude,
      @Param(name = "longitude") float longitude)
  {
    Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(id));
    if (activity.isPresent())
    {
      paceApi.addLocation(activity.get().id, latitude, longitude);
    }
  }

  /**
   * Command to change file format to xml or json
   * @param format
   * @throws Exception
   */
  @Command(description = "Change file format to json or xml")
  public void changeFileFormat(@Param(name = "file format: xml, json") String format) throws Exception
  {
    if (format.equals("json"))
    {
      JSONFile();
      System.out.println("..");
      System.out.println("..");
    }
    else
      if (format.equals("xml"))
      {
        XMLFile();
        System.out.println("..");
        System.out.println("..");
      }
      else
      {
        System.out.println();
        System.out.println("Invalid format: " + format + " Please insert valid format");
        System.out.println();
      }
  }
  
  /**
   * Command to read from persistent store(all users + activities)
   * 
   * @throws Exception
   */
  @Command(description = "Load all users + activities")
  public void load() throws Exception
  {
    paceApi.load();
  }

  /**
   * Command to write to persistent store
   * 
   * @throws Exception
   */
  @Command(description = "Store all users + activities")
  public void store() throws Exception
  {
    paceApi.store();
  }

  /**
   * Create a command line interface using cliche library
   * 
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception
  {
    Main main = new Main(); // xml is the default file type

    Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions", main);
    shell.commandLoop();

    main.paceApi.store();
  }
}
