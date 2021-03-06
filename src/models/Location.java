package models;

import com.google.common.base.Objects;

public class Location
{

  static Long counter = 0l;

  public Long id;
  public float latitude;
  public float longitude;

  public Location()
  {
  }

  /**
   * Location Constructor
   * @param latitude
   * @param longitude
   */
  public Location(float latitude, float longitude)
  {

    this.id = counter++;
    this.latitude = latitude;
    this.longitude = longitude;
  }
  
  /** 
   * @return string representation
   */
  @Override
  public String toString()
  {
    return latitude + "," + longitude;
  }
  
  /**
   * @return a hash code value
   */
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.id, this.latitude, this.longitude);
  }
  
  /** 
   * @return true if other object is equal to this one, otherwise return false
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude)
          && Objects.equal(longitude, other.longitude);
    }
    else
    {
      return false;
    }
  }
}
