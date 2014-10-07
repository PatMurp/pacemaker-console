package models;

import static com.google.common.base.Objects.toStringHelper;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

public class User
{

  static Long counter = 0l;

  public Long id;

  public String firstName;
  public String lastName;
  public String email;
  public String password;

  
  public Map<Long, Activity> activities = new HashMap<>();

  public User()
  {
  }

  /**
   * User Constructor
   * @param firstName
   * @param lastName
   * @param email
   * @param password
   */
  public User(String firstName, String lastName, String email, String password)
  {
    this.id = counter++;

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  /** 
   * @return string representation
   */
  @SuppressWarnings("deprecation")
  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id)
                               .addValue(firstName)
                               .addValue(lastName)
                               .addValue(password)
                               .addValue(email)
                               .addValue(activities)
                               .toString();
  }
  
  

  /**
   * @return a hash code value
   */
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.lastName, this.firstName, this.email, this.password);
  }
  
  /** 
   * @return true if other object is equal to this one, otherwise return false
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof User)
    {
      final User other = (User) obj;
      return Objects.equal(firstName, other.firstName)
          && Objects.equal(lastName, other.lastName)
          && Objects.equal(email, other.email)
          && Objects.equal(password, other.password)
          && Objects.equal(activities, other.activities);
    }
    else
    {
      return false;
    }
  }
}
