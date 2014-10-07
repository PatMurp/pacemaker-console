package models;

import static com.google.common.base.Objects.toStringHelper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class Activity
{

  static Long counter = 0l;

  public Long id;

  public String type;
  public String location;
  public double distance;
  
  public String startTime;
  public Long duration;

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
  public Activity(String type, String location, double distance)
  {
    this.id = counter++;
    this.type = type;
    this.location = location;
    this.distance = distance;
  }

  /** 
   * @return string representation
   */
  @SuppressWarnings("deprecation")
  @Override
  public String toString()
  {
    return toStringHelper(this) .addValue(id)
                                .addValue(type)
                                .addValue(location)
                                .addValue(distance)
                                .addValue(route)
                                .toString();
  }

  /**
   * @return a hash code value
   */
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.id, this.type, this.location, this.distance);
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
          && Objects.equal(route, other.route);
    }
    else
    {
      return false;
    }
  }
}
