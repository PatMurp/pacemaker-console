package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;

import com.google.common.base.Objects;

public class Activity
{

  static Long counter = 0l;

  public Long id;

  public String type;
  public String location;
  public double distance;
  public String starttime;
  public String duration;

  public List<Location> route = new ArrayList<>();

  public Activity()
  {
  }

  /**
   * Activity Constructor
   * @param type
   * @param location
   * @param distance
   */
  public Activity(String type, String location, double distance, String starttime, String duration)
  {
    this.id = counter++;
    this.type = type;
    this.location = location;
    this.distance = distance;
    this.starttime = starttime;
    this.duration = duration;
  }
  
  /** 
   * @return string representation
   */
  @Override
  public String toString()
  {
    return toStringHelper(this) .addValue(id)
                                .addValue(type)
                                .addValue(location)
                                .addValue(distance)
                                .addValue(starttime)
                                .addValue(duration)
                                .addValue(route)
                                .toString();
  }

  /**
   * @return a hash code value
   */
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.id, this.type, this.location, this.distance, this.starttime, this.duration);
  }
  
  
  /** 
   * @return true if other object is equal to this one, otherwise return false
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type)
          && Objects.equal(location, other.location)
          && Objects.equal(distance, other.distance)
          && Objects.equal(starttime, other.starttime)
          && Objects.equal(duration, other.duration)
          && Objects.equal(route, other.route);
    }
    else
    {
      return false;
    }
  }
  
  /**
   * Convert hours minutes and seconds into seconds ISO 8601 format
   * @param durationInput
   * @return seconds string in ISO 8601 format
   */
  public static String parseDuration(String durationInput)
  {
    LocalTime dur = LocalTime.parse(durationInput);
    Seconds durSeconds = Seconds.seconds(dur.getMillisOfDay() / 1000);
    return durSeconds.toString();
  }
  
  /**
   * Convert a date time string into IS0 8601 format
   * @param starttime2
   * @return time string in ISO 8601 format 
   */
  public static String parseStartTime(String starttime2)
  {
    // input: day:month:year 24hour:minute:seconds
    DateTime dateTime = DateTime.parse(starttime2, DateTimeFormat.forPattern("dd:MM:yyyy hh:mm:ss"));
    return dateTime.toString();
  }
}
