package temp.gvm.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

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