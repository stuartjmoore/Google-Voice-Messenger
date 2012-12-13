package temp.gvm.api;

import java.util.List;

import org.json.JSONObject;

/**
 * A conversation is a grouping of back-and-forth messages Google Voice groups
 * them for us
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

    /**
     * Creates a new conversation from a Google Voice JSON response object
     */
    public Conversation(JSONObject gvJSON) {

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
}