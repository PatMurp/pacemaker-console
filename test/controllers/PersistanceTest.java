package controllers;

import static org.junit.Assert.*;

import java.io.File;

import models.Activity;
import models.Location;
import models.User;
import controllers.PacemakerAPI;
import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

import org.junit.Test;

import utils.JSONSerializer;
import utils.Serializer;
import utils.XMLSerializer;

public class PersistanceTest
{
  PacemakerAPI pacemaker;
  
  void populate (PacemakerAPI pacemaker)
  {
    for (User user : users)
    {
      pacemaker.createUser(user.firstname, user.lastname, user.email, user.password);
    }
    User user1 = pacemaker.getUserByEmail(users[0].email);
    Activity activity = pacemaker.createActivity(user1.id, activities[0].type, activities[0].location, activities[0].distance,
        activities[0].starttime, activities[0].duration);
    pacemaker.createActivity(user1.id, activities[1].type, activities[1].location, activities[1].distance,
        activities[1].starttime, activities[1].duration);
    
    User user2 = pacemaker.getUserByEmail(users[1].email);
    pacemaker.createActivity(user2.id, activities[2].type, activities[2].location, activities[2].distance,
        activities[2].starttime, activities[2].duration);
    pacemaker.createActivity(user2.id, activities[3].type, activities[3].location, activities[3].distance,
        activities[3].starttime, activities[3].duration);
    
    for (Location location : locations)
    {
      pacemaker.addLocation(activity.id, location.latitude, location.longitude);
    }
  }
  
  void deleteFile(String fileName)
  {
    File datastore = new File("testdatastore.JSON");
    if (datastore.exists())
    {
      datastore.delete();
    }
  }

  @Test
  public void testPopulate()
  { 
    pacemaker = new PacemakerAPI(null);
    assertEquals(0, pacemaker.getUsers().size());
    populate (pacemaker);
    
    assertEquals(users.length, pacemaker.getUsers().size());
    assertEquals(2, pacemaker.getUserByEmail(users[0].email).activities.size());
    assertEquals(2, pacemaker.getUserByEmail(users[1].email).activities.size());   
    Long activityID = pacemaker.getUserByEmail(users[0].email).activities.keySet().iterator().next();
    assertEquals(locations.length, pacemaker.getActivity(activityID).route.size());   
  }
  
  @Test
  public void testXMLSerializer() throws Exception
  {
    String datastoreFile = "testdatastore.JSON";
    deleteFile (datastoreFile);
    
    Serializer serializer = new JSONSerializer(new File(datastoreFile));
    
    pacemaker = new PacemakerAPI(serializer);
    populate(pacemaker);
    pacemaker.store();
    
    PacemakerAPI pacemaker2 = new PacemakerAPI(serializer);
    pacemaker2.load();
    
    assertEquals(pacemaker.getUsers().size(), pacemaker2.getUsers().size());
    for (User user : pacemaker.getUsers())
    {
      assertTrue(pacemaker2.getUsers().contains(user));
    }
    deleteFile("testdatastore.JSON");
  }

}
