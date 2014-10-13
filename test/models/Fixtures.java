package models;

public class Fixtures
{
  public static User[] users =
    {
      new User ("marge", "simpson", "marge@simpson.com",  "secret"),
      new User ("lisa",  "simpson", "lisa@simpson.com",   "secret"),
      new User ("bart",  "simpson", "bart@simpson.com",   "secret"),
      new User ("maggie","simpson", "maggie@simpson.com", "secret")
    };

    public static Activity[] activities =
    {
      new Activity ("walk",  "fridge", 0.001, "12:10:2013 9:00:00", "1:00:00"),
      new Activity ("walk",  "bar",    1.0, "03:11:2013 11:15:00", "0:15:00"),
      new Activity ("run",   "work",   2.2, "21:05:2013 8:00:00", "0:30:00"),
      new Activity ("walk",  "shop",   2.5, "05:07:2013 11:30:00", "0:10:00"),
      new Activity ("cycle", "school", 4.5, "30:11:2013 09:00:00", "1:10:00")
    };

    public static Location[]locations =
    {
      new Location(23.3f, 33.3f),
      new Location(34.4f, 45.2f),  
      new Location(25.3f, 34.3f),
      new Location(44.4f, 23.3f)       
    };
}
