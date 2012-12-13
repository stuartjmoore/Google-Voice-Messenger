package temp.gvm.api;

import java.util.List;

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

    public Voice() {
    }

    public Voice(String username, String password) {
    }

    public Voice(String username, String password, String phonenumber) {
    }

    /**
     * Log the user in
     * 
     * @return Whether the login request was successful
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
