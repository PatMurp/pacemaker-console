package controllers;

import static org.junit.Assert.*;

import models.Activity;
import models.Location;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.PacemakerAPI;
import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class PacemakerAPITest
{

  private PacemakerAPI pacemaker;
  
  @Before
  public void setup()
  {
    pacemaker = new PacemakerAPI(null);
    for (User user : users)
    {
      pacemaker.createUser(user.firstname, user.lastname, user.email, user.password);
    }
  }
  
  @After
  public void tearDown()
  {
    pacemaker = null;
  }
  
  @Test
  public void testUser()
  {
    assertEquals(users.length, pacemaker.getUsers().size());
    
    pacemaker.createUser("homer", "simson", "homer@simpson.com", "secret");
    assertEquals(users.length+1, pacemaker.getUsers().size());
    assertEquals(users[0], pacemaker.getUserByEmail(users[0].email));
  }
  
  @Test
  public void testUsers()
  {
    assertEquals(users.length, pacemaker.getUsers().size());
    for (User user : users)
    {
      User eachUser = pacemaker.getUserByEmail(user.email);
      assertEquals(user, eachUser);
      assertNotSame(user, eachUser);
    }
  }
  
  @Test
  public void testDeleteUsers()
  {
    assertEquals (users.length, pacemaker.getUsers().size());
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    pacemaker.deleteUser(marge.id); 
    assertEquals (users.length-1, pacemaker.getUsers().size());
  }
  
  @Test
  public void testAddActivity()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Activity activity = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,
        activities[0].starttime, activities[0].duration);
    Activity returnedActivity = pacemaker.getActivity(activity.id);
    
    String expectedStartTime = pacemaker.getActivity(activity.id).starttime;
    assertEquals("2013-10-12T09:00:00.000+01:00", expectedStartTime);
    
    String expectedDuration = pacemaker.getActivity(activity.id).duration;
    assertEquals("PT3600S", expectedDuration);
    
    assertNotEquals(activities[0], returnedActivity);
    assertNotSame(activities[0], returnedActivity);
  }
  

  
  @Test
  public void testAddActivityWithSingleLocation()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,
        activities[0].starttime, activities[0].duration).id;
    
    pacemaker.addLocation(activityId, locations[0].latitude, locations[0].longitude);
    
    Activity activity = pacemaker.getActivity(activityId);
    assertEquals(1,activity.route.size());
    assertEquals(0.0001, locations[0].latitude, activity.route.get(0).latitude);
    assertEquals(0.0001, locations[0].longitude, activity.route.get(0).longitude);
  }
  
  @Test 
  public void testAddActivityWithMultipleLocation()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    Long activityId = pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,
        activities[0].starttime, activities[0].duration).id;
    
    for (Location location : locations)
    {
      pacemaker.addLocation(activityId, location.latitude, location.longitude);
    }
    
    Activity activity = pacemaker.getActivity(activityId);
    assertEquals (locations.length, activity.route.size());
    int i = 0;
    for (Location location : activity.route)
    {
      assertEquals(location, locations[i]);
      i++;
    }
  }
  
  @Test
  public void getUserActivities()
  {
    User marge = pacemaker.getUserByEmail("marge@simpson.com");
    User bart = pacemaker.getUserByEmail("bart@simpson.com");
    pacemaker.createActivity(marge.id, activities[0].type, activities[0].location, activities[0].distance,
        activities[0].starttime, activities[0].duration);
    pacemaker.createActivity(marge.id, activities[1].type, activities[1].location, activities[1].distance,
        activities[1].starttime, activities[1].duration);
    
    pacemaker.createActivity(bart.id, activities[2].type, activities[2].location, activities[2].distance,
        activities[2].starttime, activities[2].duration);
    
    assertEquals(2, pacemaker.getUserActivities(marge.id).size());
    assertEquals(1, pacemaker.getUserActivities(bart.id).size());
  }
}
