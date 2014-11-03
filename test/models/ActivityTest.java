package models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ActivityTest
{
  
  Activity a1 = new Activity ("walk", "town", 0.001,"12:10:2013 9:00:00", "1:00:00");
  Activity a2 = new Activity ("jog",  "shop",   2.5, "05:07:2013 11:30:00", "1:10:00");
  Activity a3 = new Activity ("cycle", "school", 4.5, "30:11:2013 09:00:00", "0:10:00");
  Activity a4 = new Activity ("run",   "work",   2.2, "21:05:2013 8:00:00", "0:30:00");
  
  @Test
  public void testCreate()
  {
    assertEquals("walk",        a1.type);
    assertEquals("town",      a1.location);
    assertEquals(0.0001, 0.001, a1.distance);
    assertEquals("12:10:2013 9:00:00", a1.starttime);
    assertEquals("1:00:00", a1.duration);
  }
  
  @Test
  public void testToString()
  {
    assertEquals("Activity{" + a1.id + ", walk, town, 0.001, 12:10:2013 9:00:00, 1:00:00, []}", a1.toString());
  }
  
  @Test
  public void testParseStartTime()
  {
    String input = "12:10:2013 9:00:00";
    
    String output = Activity.parseStartTime(input);
    assertNotEquals(output, input);
    assertEquals("2013-10-12T09:00:00.000+01:00", output);
  }
  
  @Test
  public void testParseDuration()
  {
    String durInput = "1:00:00";
    String durOutput = Activity.parseDuration(durInput);
    assertNotEquals(durInput, durOutput);
    assertEquals("PT3600S", durOutput);
  }
  
  @Test
  public void testCompareTo()
  {
   Map<Long, Activity> uActiv = new HashMap<>();
   uActiv.put( (long) 0, a1);
   uActiv.put( (long) 1, a2);
   uActiv.put( (long) 2, a3);
   uActiv.put( (long) 3, a4);
   List<Activity> userActiv = new ArrayList<>(uActiv.values());
    
   assertEquals(a1, userActiv.get(0)); //natural order
   assertEquals(a4, userActiv.get(3));
   
   Collections.sort(userActiv, Activity.compareType);
   assertEquals("cycle", userActiv.get(0).type);
   assertEquals("walk", userActiv.get(3).type);
   
   Collections.sort(userActiv, Activity.compareLocation);
   assertEquals("school", userActiv.get(0).location);
   assertEquals("work", userActiv.get(3).location);
   
   Collections.sort(userActiv, Activity.compareDistance);
   assertEquals(0.001, userActiv.get(0).distance, 0.01);
   assertEquals(4.5, userActiv.get(3).distance, 0.01);
   
   Collections.sort(userActiv, Activity.compareDate);
   assertEquals("05:07:2013 11:30:00",userActiv.get(0).starttime);
   assertEquals("30:11:2013 09:00:00", userActiv.get(3).starttime);
   
   Collections.sort(userActiv, Activity.compareDuration);
   assertEquals("0:10:00", userActiv.get(0).duration);
   assertEquals("1:10:00", userActiv.get(3).duration);
  }
}
