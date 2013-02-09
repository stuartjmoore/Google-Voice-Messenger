package temp.gvm.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.util.Log;

/**
 * A single message
 */
public class Message
{
    private class HTMLQuery
    {
        public static final String Text = "span.gc-message-sms-text";
        public static final String Time = "span.gc-message-sms-time";
        public static final String From = "span.gc-message-sms-from";
    }

    private class JSONKeys
    {
        public static final String Id        = "id";
        public static final String Text      = "messageText";
        public static final String Type      = "type";
        public static final String StartTime = "startTime";
    }

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

    private Person      _from = null;
    private Date        _date = null;
    private String      _text = null;
    private MESSAGETYPE _type = MESSAGETYPE.NOTINIT;
    private String      _id   = null;

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
     * 
     * @throws JSONException
     */
    public Message(JSONObject gvJSON) throws JSONException
    {
        Long epoch = gvJSON.getLong(JSONKeys.StartTime);

        _id = gvJSON.getString(JSONKeys.Id);
        _text = gvJSON.getString(JSONKeys.Text);
        _type = MESSAGETYPE.fromGVCode(gvJSON.getInt(JSONKeys.Type));
        _date = new Date(epoch);

        gvJSON.remove(_id);
    }

    public Message(Element htmlNode, Person contact)
    {
        _text = htmlNode.select(HTMLQuery.Text).first().text();
        String from = htmlNode.select(HTMLQuery.From).first().text()
                .replaceAll(":", "");
        if (from.toUpperCase() == "ME") {
            _from = null; // TODO: we need a 'Me' contact
            _type = MESSAGETYPE.SENT;
        } else {
            _from = contact;
            _type = MESSAGETYPE.RECEIVED;
        }
        SimpleDateFormat time = new SimpleDateFormat("MM/dd/yy h:m a");
        /*
         * try { _date =
         * time.parse(htmlNode.select(XPathQuery.Time).first().data()); } catch
         * (ParseException e) { Log.e(this.getClass().getName(), e.getMessage(),
         * e); }
         */
    }

    public boolean send()
    {
        return false;
    }
}
