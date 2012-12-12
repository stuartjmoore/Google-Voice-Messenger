import android.util;
import android.net.http.AndroidHttpClient;
import android.provider.ContactsContract;

/**
 * Main access point to the Google Voice servers
 *
 * Will this class be a singleton? Will the tokens be static?
 */
public class Voice
{
    private String _username = null;
    private String _password = null;
    private String _phoneNumber = null;
    
    // Needed: Tokens
    
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
     * Log the user in
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
    
    public List<Conversation> getTrash()
    {
    }
    
    public List<Conversation> getSpam()
    {
    }
	
    /**
     * Not for version 1.0, but it's always good to think ahead
     */
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
	
    public List<Conversation> search(String query)
    {
    }
    public List<Conversation> getConversationsFrom(Person person)
    {
        // search(person.phoneNumber);
        // remove conversations not from person
    }
    
    private JSONObject execute(String command)
    {
    }
}

/**
 * A conversation is a grouping of back-and-forth messages
 * Google Voice groups them for us
 */
public class Conversation
{
    private List<Person> _contacts; // Future proofing
    private List<Message> _messages;

    private String _id = null;
    private boolean _isStarred = false;
    private boolean _isSpam = false;
    private boolean _isRead = false;
    private boolean _isNew = false;
    
    public boolean isStarred() { return _isStarred; }
    public boolean isSpam() { return _isSpam; }
    public boolean isRead() { return _isRead; }
    public boolean isNew() { return _isNew; }
    
    /**
     * Creates a new conversation from a Google Voice JSON response object
     */
    public Conversation(JSONObject gvJSON)
    {
    	
    }

    /**
     * Deletes a conversation
     *
     * @param	msg	Item to be deleted
     * @return	Whether the item was deleted
     */
    public boolean delete()
    {
    }
	
    /**
     * Restores an item from trash
     *
     * @param	msg	Item to be restored
     * @return	Whether the item was restored
     */
    public boolean retore()
    {
    }
	
    /**
     * Stars or un-stars an item
     *
     * @param	msg	Item to be starred/un-starred
     * @return	Whether the item was successfully starred/un-starred
     */
    public boolean toggleStar()
    {
    }
	
    /**
     * Marks or un-marks as spam
     *
     * @param	msg	Item to be marked spam/not spam
     * @return	Whether the item was successfully marked/unmarked as spam
     */
    public boolean toggleSpam()
    {
    }
	
    public boolean toggleRead()
    {
    }
	
    private boolean archive()
    {
    }
	
    private boolean unarchive()
    {
    }
}

/**
 * A single message
 */
public class Message
{
    public enum MESSAGETYPE
    {
    	NOTINIT (0),
        SENT (11), // 10 : recieved, 11 : sent (from GV)
        RECEIVED (10);

        private final int _gvValue;   

        MESSAGETYPE(int val)
        {
            this._gvValue = val;
        }

        public int value()
        { 
            return _gvValue; 
        }
        
        public static MESSAGETYPE fromGVCode(int gv)
        {
            if( gv == SENT.value() ) return SENT;
            if( gv == RECEIVED.value() ) return RECEIVED;
            
            return NOTINIT;
        }
    }
    
    private Person _from = null;
    private Date _date = null;
    private String _text = null;
    private MESSAGETYPE _type = new MESSAGETYPE.NOTINIT;
    private String _id = null;
    private boolean _isRead = false;
    private boolean _isSpam = false;
    private boolean _isTrash = false;
    private boolean _isStarred = false;
    
    public Person from() { return _from; }
    public Date date() { return _date; }
    public String text() { return _text; }
    public MESSAGETYPE type() { return _type; }
    public String id() { return _id; }
    public boolean isRead() { return _isRead; }
    public boolean isSpam() { return _isSpam; }
    public boolean isTrash() { return _isTrash; }
    public boolean isStarred() { return _isStarred; }
    
    /**
     * Creates a new message from a Google Voice JSON response object
     */
    public Message(JSON gvJSON)
    {
    	String[] keys = gvJSON.getNames(); //First is ID
    	JSONObject msgJSON = null;
    	
    	if( keys.length == 0 ) throw new UnsupportedOperationException();
    	_id = keys[0];
    	
    	msgJSON = gvJSON.getJSONObject(_id);
    	
    	//Hopefully GV doesn't change their date format.
    	_date = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH).parse(msgJSON.getString();
    	_text = msgJSON.getString("messageText");
    	_type = MESSAGETYPE.fromGVCode(msgJSON.getInt("type"));
    	_isRead = msgJSON.getBoolean("isRead");
    	_isSpam = msgJSON.getBoolean("isSpam");
    	_isTrash = msgJSON.getBoolean("isTrash");
    	_isStarred = msgJSON.getBoolean("star");
    	
    	gvJSON.remove(_id);
    }
    
    public boolean send()
    {
    }
}

/**
 * Not for version 1.0, but it's always good to think ahead
 */
public class AudioMessage extends Message
{
	// TODO: store mp3 and ogg for recorded calls and voicemails
}

/**
 * A contact
 *
 * Needs to be linked with the user's contact list.
 */
public class Person
{
    private String _lookupKey = null; // Key to connect GV contact to device list
    
    private String _nameDisplay = null;
    private String _nameGiven = null; // From Contacts database, may be needed for
    private String _nameFamily = null; // UI bolding and wording
    private String _nameOnServer = null;
    
    private String _phoneNumber = null;
    private String _phoneNumberDisplay = null;
    private int _phoneType = 0; // 7 : mobile (from GV)
    
    private String _photoURI = null; // Local device photo
    private String _photoThumbnailURI = null; // Local device photo
    private String _photoURL = null; // Server GV photo
}
