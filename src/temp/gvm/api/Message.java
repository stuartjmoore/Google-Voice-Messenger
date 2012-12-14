package temp.gvm.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A single message
 */
public class Message
{
    public enum MESSAGETYPE
    {
        // From Google Voice
        NOTINIT(0), SENT(11), RECEIVED(10);

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
            if (gv == SENT.value())
                return SENT;
            if (gv == RECEIVED.value())
                return RECEIVED;

            return NOTINIT;
        }
    }

    private Person _from = null;
    private Date _date = null;
    private String _text = null;
    private MESSAGETYPE _type = MESSAGETYPE.NOTINIT;
    private String _id = null;

    public Person from()
    {
        return _from;
    }

    public Date date()
    {
        return _date;
    }

    public String text()
    {
        return _text;
    }

    public MESSAGETYPE type()
    {
        return _type;
    }

    public String id()
    {
        return _id;
    }

    /**
     * Creates a new message from a Google Voice JSON response object
     * @throws JSONException 
     */
    public Message(JSONObject gvJSON) throws JSONException
    {
        // s tart_time is a number, but it's too large to be seconds
        Long epoch = gvJSON.getLong("start_time");

        _id = gvJSON.getString("id");
        _text = gvJSON.getString("message_text");
        _type = MESSAGETYPE.fromGVCode(gvJSON.getInt("type"));
        _date = new Date(epoch);

        gvJSON.remove(_id);
    }

    public boolean send()
    {
        return false;
    }
}
