import android.util;
import android.net.http.AndroidHttpClient;

public class Voice
{
    private String username = null;
    private String password = null;
    private String phoneNumber = null;
    
    public Voice()
    {
    }
    
    public void login()
    {
    }
    
    public List<SMS> getInbox()
    {
    }
    public List<SMS> getStarred()
    {
    }
    public List<SMS> getAllSMS()
    {
    }
    public List<SMS> getTrash()
    {
    }
    public List<SMS> getSpam()
    {
    }
    
    public boolean sendSMS(Person to, String message)
    {
    }
}

public class SMS
{
    private Person from = null;
    private Date date = null;
    private String message = null;
}

public class Person
{
    private String firstName = null;
    private String lastName = null;
    private String phoneNumber = null;
}