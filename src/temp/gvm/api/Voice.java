package temp.gvm.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main access point to the Google Voice servers
 * 
 * Will this class be a singleton? Will the tokens be static?
 */
public class Voice
{
    private String _token = null;
    private String _phoneNumber = null;

    public Voice(String token)
    {
        this.setToken(token);
    }

    public void setToken(String token)
    {
        this._token = token;
    }

    private List<Conversation> getConversations(JSONObject gvJSON)
    {
        JSONArray tmpConversations = null;
        List<Conversation> ret = new ArrayList<Conversation>();
        Conversation tmpConvo = null;

        try {
            tmpConversations = gvJSON.getJSONArray("conversation");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        for (int i = 0; i < tmpConversations.length(); i++) {
            try {
                tmpConvo = new Conversation(tmpConversations.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
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
        try {
            URL url;
            url = new URL(command);
            URLConnection conn = url.openConnection();
            // This is how we authenticate without needing Username & Password.  Token is provided by the App.
            conn.setRequestProperty("Authorization", "GoogleLogin auth=" + this._token);
            // User agent is required for getting the mobile pages, which is what most API calls pull data from.
            conn.setRequestProperty("User-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n\r");
            }
            rd.close();
            String res = sb.toString();
            
            // TODO Find and return the content that needs to be returned
            return null;
        } catch (Exception e) {
            // TODO Error Handling
            e.printStackTrace();
            return null;
        }
    }
}
