package temp.gvm.api;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.*;
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
     * @return Whether the login request was successful
     */
    public boolean login()
    {
        return false;
    }

    private List<Conversation> getConversations(JSONObject gvJSON)
    {
        JSONArray tmpConversations = null;
        List<Conversation> ret = new List<Conversation>();
        Conversation tmpConvo = null;

        tmpConversations = gvJSON.getJSONArray("conversation");

        for (int i = 0; i < tmpConversations.length(); i++) {
            tmpConvo = new Conversation(tmpConversations.getJSONObject(i));
            ret.add(tmpConvo);
        }

        return ret;
    }

    public List<Conversation> getInbox()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);

    }

    public List<Conversation> getStarred()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);
    }

    public List<Conversation> getText()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);
    }

    public List<Conversation> getTrash()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);
    }

    public List<Conversation> getSpam()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);
    }

    /**
     * Not for version 1.0, but it's always good to think ahead
     */
    public List<Conversation> getHistory()
    {
        JSONObject gvJSON = null;

        // TODO:
        // gvJSON = execute(..)

        return getConversations(gvJSON);
    }

    public List<Conversation> getRecordedCalls()
    {
        return null;
    }

    public List<Conversation> getPlacedCalls()
    {
        return null;
    }

    public List<Conversation> getReceivedCalls()
    {
        return null;
    }

    public List<Conversation> getMissedCalls()
    {
        return null;
    }

    public List<Conversation> search(String query)
    {
        return null;
    }

    public List<Conversation> getConversationsFrom(Person person)
    {
        return null;
        // search(person.phoneNumber);
        // remove conversations not from person
    }

    private JSONObject execute(String command)
    {
        return null;
    }
}
