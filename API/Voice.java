import android.util;
import android.net.http.AndroidHttpClient;

public class Voice
{
    private String _username = null;
    private String _password = null;
    private String _phoneNumber = null;
	    
    public Voice()
    {
    }
	
	public Voice(String username, String password)
	{
	}
	
	public Voice(String username, String password, String phonenumber)
	{
	
	}
    
	/**
	* 
	*
	* @return	Whether the login request was successful
	*/
    public boolean login()
    {
    }
	
    
    public List<Conversation> getInbox()
    {
    }
	
    public List<Conversation> getStarred()
    {
    }
	
    public List<Conversation> getText()
    {
    }
	
	public List<Conversation> getHistory()
	{
	}
	
	public List<Conversation> getRecordedCalls()
	{
	}
	
	public List<Conversation> getPlacedCalls()
	{
	}
	
	public List<Conversation> getReceivedCalls()
	{
	}
	
	public List<Conversation> getMissedCalls()
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
    
    public boolean sendSMS(Message msg)
    {
    }
	
	/**
	*
	* @param	msg	Item to be deleted
	* @return	Whether the item was deleted
	*/
	public boolean deleteItem(Message msg)
	{
	}
	
	/**
	* Restores an item from trash
	*
	* @param	msg	Item to be restored
	* @return	Whether the item was restored
	*/
	public boolean retoreItem(Message msg)
	{	
	}
	
	/**
	* Stars or un-stars an item
	*
	* @param	msg	Item to be starred/un-starred
	* @return	Whether the item was successfully starred/un-starred
	*/
	public boolean toggleStar(Message msg)
	{
	}
	
	/**
	* Marks or un-marks as spam
	*
	* @param	msg	Item to be marked spam/not spam
	* @return	Whether the item was successfully marked/unmarked as spam
	*/
	public boolean toggleSpam(Message msg)
	{
	}
	
	public boolean toggleRead(Message msg)
	{
	
	}
	
	private boolean archive(Message msg)
	{
	}
	
	private boolean unarchive(Message msg)
	{
	}
	
	private JSONObject execute(String command)
	{
	}
}

/*
 *  A conversation is a grouping of single back-and-forth messages.
 *  Google Voice groups them for us.
 */
public class Conversation
{
	private Person _contact;
    private List<Message> _messages;
}

/*
 *  A single message.
 */
public class Message
{
    private Person _from = null;
    private Date _date = null;
	private String _transcript = null;
	private String _id = null;
	private boolean _isStarred = false;
	private boolean _isSpam = false;
	private boolean _isRead = false;
	private boolean _isNew = false;
}

public class AudioMessage extends Message
{
	// TODO: store mp3 and ogg for recorded calls and voicemails
}

/*
 *  A person.
 *  
 *  Needs to some how be linked with the user's contact list.
 */
public class Person
{
    private String _name = null;
    private String _nickname = null;
    private String _phoneNumber = null;
}
