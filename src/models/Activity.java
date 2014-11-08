package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.impl.CollectionASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import com.google.common.base.Objects;

public class Activity implements Comparable<Activity>
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
   * @return id
   */
  public Long getId()
  {
    return id;
  }

  /**
   * @param id
   */
  public void setId(Long id)
  {
    this.id = id;
  }

  /**
   * @return type
   */
  public String getType()
  {
    return type;
  }

  /**
   * @param type
   */
  public void setType(String type)
  {
    this.type = type;
  }

  /**
   * @return location
   */
  public String getLocation()
  {
    return location;
  }

  /**
   * @param location
   */
  public void setLocation(String location)
  {
    this.location = location;
  }

  /**
   * @return distance
   */
  public double getDistance()
  {
    return distance;
  }

  /**
   * @param distance
   */
  public void setDistance(double distance)
  {
    this.distance = distance;
  }

  /**
   * @return
   */
  public String getStarttime()
  {
    return starttime;
  }

  /**
   * @param starttime
   */
  public void setStarttime(String starttime)
  {
    this.starttime = starttime;
  }

  /**
   * @return duration
   */
  public String getDuration()
  {
    return duration;
  }

  /**
   * @param duration
   */
  public void setDuration(String duration)
  {
    this.duration = duration;
  }

  /**
   * @return route
   */
  public List<Location> getRoute()
  {
    return route;
  }

  /**
   * @param route
   */
  public void setRoute(List<Location> route)
  {
    this.route = route;
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
  public static String parseStartTime(String starttime)
  {
    // input: day:month:year 24hour:minute:seconds
    DateTime dateTime = DateTime.parse(starttime, DateTimeFormat.forPattern("dd:MM:yyyy hh:mm:ss"));
    return dateTime.toString();
  }

 
  /**
   * default comparator sort activities by id
   */
  @Override
  public int compareTo(Activity o)
  {
    return (int) (this.id -o.id);
  }
  
  /**
   * sort activities by type
   */
  public static Comparator<Activity> compareType = new Comparator<Activity>()
  {
    @Override
    public int compare(Activity o1, Activity o2)
    {
      return o1.type.compareTo(o2.type);
    }
  };
  
  
  /**
   * sort activities by location
   */
  public static Comparator<Activity> compareLocation = new Comparator<Activity>()
  {
    @Override
    public int compare(Activity o1, Activity o2)
    {
      return o1.location.compareTo(o2.location);
    }
  };
  
  /**
   * sort activities by distance
   */
  public static Comparator<Activity> compareDistance = new Comparator<Activity>()  
  {
    @Override
    public int compare(Activity o1, Activity o2)
    {
      return new Double (o1.distance).compareTo(o2.distance);
    }
  };
  
  /**
   * sort activities by date
   */
  public static Comparator<Activity> compareDate = new Comparator<Activity>()
  {
    @Override
    public int compare(Activity o1, Activity o2)
    {
      return o1.starttime.compareTo(o2.starttime);
    }
  };
  
  /**
   * sort activities by duration
   */
  public static Comparator<Activity> compareDuration = new Comparator<Activity>()
  {
    @Override
    public int compare(Activity o1, Activity o2)
    {
      return o1.duration.compareTo(o2.duration);
    }
  };
     
  /**
   * Display activities in tabular format using btc-ascii-table 1.0 library 
   * @param activities
   */
  @SuppressWarnings("rawtypes")
  public static void activityTable(List activities)
  {
    @SuppressWarnings("unchecked")
    IASCIITableAware activTable =
        new CollectionASCIITableAware<>(activities, 
            "id", "type", "location", "distance", "starttime", "duration", "route");
    ASCIITable.getInstance().printTable(activTable);
  }
}
