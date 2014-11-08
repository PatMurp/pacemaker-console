package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.impl.CollectionASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import com.google.common.base.Objects;

public class User
{

  static Long counter = 0l;

  public Long id;

  public String firstname;
  public String lastname;
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
  public User(String firstname, String lastname, String email, String password)
  {
    this.id = counter++;

    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
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
   * @return first name
   */
  public String getFirstname()
  {
    return firstname;
  }

  /**
   * @param firstname
   */
  public void setFirstname(String firstname)
  {
    this.firstname = firstname;
  }

  /**
   * @return last name
   */
  public String getLastname()
  {
    return lastname;
  }

  /**
   * @param lastname
   */
  public void setLastname(String lastname)
  {
    this.lastname = lastname;
  }

  /**
   * @return email
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * @param email
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * @return password
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * @param password
   */
  public void setPassword(String password)
  {
    this.password = password;
  }


  /** 
   * @return string representation
   */
  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id)
                               .addValue(firstname)
                               .addValue(lastname)
                               .addValue(email)
                               .addValue(password)
                               .addValue(activities)
                               .toString();
  }
  

  /**
   * @return a hash code value
   */
  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.lastname, this.firstname, this.email, this.password);
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
      return Objects.equal(firstname, other.firstname)
          && Objects.equal(lastname, other.lastname)
          && Objects.equal(email, other.email)
          && Objects.equal(password, other.password)
          && Objects.equal(activities, other.activities);
    }
    else
    {
      return false;
    }
  }
  
  /**
   * Display user details in tabular format using btc-ascii-table 1.0 library
   * @param users
   */
  @SuppressWarnings("rawtypes")
  public static void userTable(List users)
  {
    @SuppressWarnings("unchecked")
    IASCIITableAware usersTable =
        new CollectionASCIITableAware<>(users, 
            "id", "firstname", "lastname", "email", "password");
    ASCIITable.getInstance().printTable(usersTable);
  }
  
  
  
}
