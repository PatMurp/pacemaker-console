package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActivityTest
{
  
  Activity test = new Activity ("walk", "fridge", 0.001,"12:10:2013 9:00:00", "1:00:00");
  
  @Test
  public void testCreate()
  {
    assertEquals("walk",        test.type);
    assertEquals("fridge",      test.location);
    assertEquals(0.0001, 0.001, test.distance);
    assertEquals("12:10:2013 9:00:00", test.starttime);
    assertEquals("1:00:00", test.duration);
  }
  
  @Test
  public void testToString()
  {
    assertEquals("Activity{" + test.id + ", walk, fridge, 0.001, 12:10:2013 9:00:00, 1:00:00, []}", test.toString());
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
}
