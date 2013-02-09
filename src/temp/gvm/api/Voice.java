package temp.gvm.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

import temp.gvm.Utilities;

/**
 * Main access point to the Google Voice servers
 * 
 * Will this class be a singleton? Will the tokens be static?
 */
public class Voice
{
    private class LabelURLs
    {
        public static final String Inbox = "https://www.google.com/voice/inbox/recent/inbox/";
    }

    /**
     * Starting token to identify JSON portion of XML>JSON+HTML response
     */
    public static final String JSONResponse_Start = "<json><![CDATA[";
    /**
     * Ending token to identify end of JSON portion of XML>JSON+HTML response
     */
    public static final String JSONResponse_End   = "]]></json>";
    /**
     * Starting token to identify HTML portion of XML>JSON+HTML response
     */
    public static final String HTMLResponse_Start = "<html><![CDATA[";
    /**
     * Ending token to identify end of HTML portion of XML>JSON+HTML response
     */
    public static final String HTMLResponse_End   = "]]></html>";

    private String             _token             = null;
    private String             _phoneNumber       = null;

    public Voice(String token)
    {
        this.setToken(token);
    }

    public void setToken(String token)
    {
        this._token = token;
    }

    private HashMap<String, Conversation> getConversations(String gvResponse)
    {
        JSONArray tmpConversations = null;
        HashMap<String, Conversation> ret = new HashMap<String, Conversation>();
        Conversation tmpConvo = null;
        JSONObject gvJSON = null;
        try {
            gvJSON = new JSONObject(Utilities.getSubstring(gvResponse,
                    JSONResponse_Start, JSONResponse_End, true, false, false));
            Log.i("Purple JSON",gvJSON.toString());
            
        } catch (JSONException ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
        }
        
        
        String gvHTML = Utilities.getSubstring(gvResponse, HTMLResponse_Start,
                HTMLResponse_End, true, false, false);
        
        // Assume first param is the HTML
        String htmlResponse = gvHTML;

        Document respDoc = Jsoup.parse(htmlResponse);
        List<Element> nodes = respDoc.select("div.gc-message");
        Conversation currConvo = null;

        for (Element htmlNode : nodes) {
            if (htmlNode == null)
                continue;

            currConvo = new Conversation(htmlNode);

            ret.put(currConvo.id(), currConvo);
        }

        // TODO Merge with JSON Conversation
        
        return ret;
    }

    public HashMap<String, Conversation> getInbox()
    {
        String gvResponse = null;

        gvResponse = execute(LabelURLs.Inbox);

        return getConversations(gvResponse);

    }

    public HashMap<String, Conversation> getStarred()
    {
        String gvResponse = null;

        // TODO:
        // gvResponse = execute(..)

        return getConversations(gvResponse);
    }

    public HashMap<String, Conversation> getText()
    {
        String gvResponse = null;

        // TODO:
        // gvResponse = execute(..)

        return getConversations(gvResponse);
    }

    public HashMap<String, Conversation> getTrash()
    {
        String gvResponse = null;

        // TODO:
        // gvResponse = execute(..)

        return getConversations(gvResponse);
    }

    public HashMap<String, Conversation> getSpam()
    {
        String gvResponse = null;

        // TODO:
        // gvResponse = execute(..)

        return getConversations(gvResponse);
    }

    /**
     * Not for version 1.0, but it's always good to think ahead
     */
    public HashMap<String, Conversation> getHistory()
    {
        String gvResponse = null;

        // TODO:
        // gvResponse = execute(..)

        return getConversations(gvResponse);
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

    private String execute(String command)
    {
        try {
            URL url;
            url = new URL(command);
            URLConnection conn = url.openConnection();
            // This is how we authenticate without needing Username & Password.
            // Token is provided by the App.
            conn.setRequestProperty("Authorization", "GoogleLogin auth="
                    + this._token);
            // User agent is required for getting the mobile pages, which is
            // what most API calls pull data from.
            conn.setRequestProperty(
                    "User-agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13");

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n\r");
            }
            rd.close();
            String res = sb.toString();

            return res;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            return null;
        }
    }
}
