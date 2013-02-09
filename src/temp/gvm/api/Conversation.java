package temp.gvm.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.jsoup.nodes.Element;

/**
 * A conversation is a grouping of back-and-forth messages Google Voice groups
 * them for us
 */
public class Conversation
{
    private class JSONKeys
    {

    }

    private class HTMLQuery
    {

    }

    private ArrayList<Person>        _contacts  = null; // Future proofing
    private HashMap<String, Message> _messages  = null;

    private String                   _id        = null;
    private boolean                  _isStarred = false;
    private boolean                  _isSpam    = false;
    private boolean                  _isRead    = false;
    private boolean                  _isNew     = false;

    public boolean isStarred()
    {
        return _isStarred;
    }

    public boolean isSpam()
    {
        return _isSpam;
    }

    public boolean isRead()
    {
        return _isRead;
    }

    public boolean isNew()
    {
        return _isNew;
    }

    public Date date()
    {
        // Get oldest message, return date
        return null;
    }

    public String id()
    {
        return _id;
    }

    /**
     * Creates a new conversation from a Google Voice JSON response object
     * 
     * @throws JSONException
     */
    public Conversation(JSONObject gvJSON) throws JSONException
    {
        _id = gvJSON.getString("id");
        _isRead = gvJSON.getBoolean("read");

        _contacts = new ArrayList<Person>();
        _contacts.add(new Person(gvJSON));

        // Get messages for this conversation
        _messages = new HashMap<String, Message>();
        JSONArray msgs = gvJSON.getJSONArray("children");
        Message tmpMessage = null;
        for (int i = 0; i < msgs.length(); i++) {
            tmpMessage = new Message(msgs.getJSONObject(i));
            if (!_messages.containsKey(tmpMessage.id()))
                _messages.put(tmpMessage.id(), tmpMessage);
        }
    }

    public Conversation(Element htmlNode)
    {
        _messages = new HashMap<String, Message>();
        Message currMessage = null;
        List<Element> nodes = htmlNode.getAllElements();

        _contacts = new ArrayList<Person>();
        _contacts.add(new Person(htmlNode));

        for (Element node : nodes) {
            currMessage = new Message(node, _contacts.get(0));
            _messages.put(currMessage.id(), currMessage);
        }
    }

    /**
     * Deletes a conversation
     * 
     * @param msg
     *            Item to be deleted
     * @return Whether the item was deleted
     */
    public boolean delete()
    {
        return false;
    }

    /**
     * Restores an item from trash
     * 
     * @param msg
     *            Item to be restored
     * @return Whether the item was restored
     */
    public boolean restore()
    {
        return false;
    }

    /**
     * Stars or un-stars an item
     * 
     * @param msg
     *            Item to be starred/un-starred
     * @return Whether the item was successfully starred/un-starred
     */
    public boolean toggleStar()
    {
        return false;
    }

    /**
     * Marks or un-marks as spam
     * 
     * @param msg
     *            Item to be marked spam/not spam
     * @return Whether the item was successfully marked/unmarked as spam
     */
    public boolean toggleSpam()
    {
        return false;
    }

    public boolean toggleRead()
    {
        return false;
    }

    private boolean archive()
    {
        return false;
    }

    private boolean unarchive()
    {
        return false;
    }

    public void merge(Conversation convMerge)
    {
        // TODO: Merge the messages
    }
}
