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
    
    public List<Conversation> getInbox()
    {
    }
    public List<Conversation> getStarred()
    {
    }
    public List<Conversation> getAllSMS()
    {
    }
    public List<Conversation> getTrash()
    {
    }
    public List<Conversation> getSpam()
    {
    }
    
    public List<Conversation> search(String query)
    {
    }
    public List<Conversation> getConversationsFrom(Person person)
    {
        // search(person.phoneNumber);
        // remove conversations not from person
    }
    
    public boolean sendSMS(Person to, String message)
    {
    }
}

/*
 *  A conversation is a grouping of single back-and-forth SMS messages.
 *  Google Voice groups them for us.
 */
public class Conversation
{
    private List<SMS> messages;
}

/*
 *  A single SMS message.
 */
public class SMS
{
    private Person from = null;
    private Date date = null;
    private String message = null;
}

/*
 *  A person.
 *  
 *  Needs to some how be linked with the user's contact list.
 */
public class Person
{
    private String firstName = null;
    private String lastName = null;
    private String phoneNumber = null;
}
