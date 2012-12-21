package temp.gvm.api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.net.Uri;
//import android.net.URL; //Why is this not working? http://developer.android.com/reference/java/net/URL.html
import android.util.Log;

/**
 * A contact
 * 
 * Needs to be linked with the user's contact list.
 */
public class Person
{
    private final class XPathQuery
    {
        public static final String Name = "descendant::a[contains(@class,gc-under')]";
        public static final String Id = "/*/*/div[@id]";
        public static final String PhoneNumber = "descendant:span[@class='gc-message-type']";
        public static final String PortraitURL = "descendant::div[@class='gc-message-portrait']";
    }
    
    private final class JSONKeys
    {
        public static final String PhoneNumber = "phoneNumber";
        public static final String PhoneNumberDisplay = "displayNumber";
    }
    
    public enum PHONETYPE
    {
        // From Google Voice
        NOTINIT(0), MOBILE(7);

        private final int _gvValue;

        PHONETYPE(int val)
        {
            this._gvValue = val;
        }

        public int value()
        {
            return _gvValue;
        }

        public static PHONETYPE fromGVCode(int gv)
        {
            if (gv == MOBILE.value())
                return MOBILE;

            return NOTINIT;
        }
    }
    
    /**
     * Base constructor
     */
    public Person()
    {
        throw new UnsupportedOperationException("Method Not Implemented");
    }
    
    /**
     * Parses a person from HTML data
     * @param htmlNode
     */
    public Person(Element htmlNode)
    {
        _nameOnServer = htmlNode.select(XPathQuery.Name).first().data();
        _id = htmlNode.select(XPathQuery.Id).first().data();
        try {
            _photoURL = new URL(htmlNode.select(XPathQuery.PortraitURL).first().data());
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            _photoURL = null;
        }
        
        Element phoneNumElement = htmlNode.select(XPathQuery.PhoneNumber).first();
        if(phoneNumElement != null) {
            _phoneNumberDisplay = phoneNumElement.data();
            _phoneNumber = getRawPhoneNumber(_phoneNumberDisplay);
        }
    }
    
    public Person(JSONObject jsonPerson) throws JSONException
    {
        _phoneNumber = jsonPerson.getString(JSONKeys.PhoneNumber);
        _phoneNumberDisplay = jsonPerson.getString(JSONKeys.PhoneNumberDisplay);
    }

    private String _id = null; //GV object ID
    private String _lookupKey = null; // Key to connect GV contact to device
                                      // list

    private String _nameDisplay = null;
    private String _nameGiven = null; // From Contacts database, may be needed
                                      // for
    private String _nameFamily = null; // UI bolding and wording
    private String _nameOnServer = null;

    private String _phoneNumber = null;
    private String _phoneNumberDisplay = null;
    private PHONETYPE _phoneType = PHONETYPE.NOTINIT; // 7 : mobile (from GV)

    private Uri _photoURI = null; // Local device photo
    private Uri _photoThumbnailURI = null; // Local device photo
    private URL _photoURL = null; // Server GV photo
    
    private String getRawPhoneNumber(String formattedNumber)
    {
        //Regex replace comma, parens, plus and space
        return formattedNumber.replaceAll("[,\\(\\)\\+ ]", "");
    }
    
    
    /**
     * Gets the GV server object ID
     * 
     * @return
     */
    public String getID() { return _id; }
    
    /**
     * Gets the key to connect device contact to GV contact
     * 
     * @return
     */
    public String getLookupKey() { return _lookupKey; }
    
    /**
     * Sets the key to connect device contact to GV contact
     * 
     * @param lookupKey
     * @return Whether key is valid and member was set
     */
    public boolean setLookupKey(String lookupKey)
    {
    	//TODO: Verify lookup key exists
    	//TODO: Check for any injection attacks (if user provided)
    	_lookupKey = lookupKey;
    	
    	return true;
    }
    
    /**
     * Gets the name of the contact to display
     * 
     * @return
     */
    public String getNameDisplay() { return _nameDisplay; }
    
    /**
     * Sets the contact's display name
     * 
     * @param nameDisplay
     * @return Whether the member was set
     */
    public boolean setNameDisplay(String nameDisplay)
    {
    	//TODO: Check for any injection attacks (if user provided)
    	_nameDisplay = nameDisplay;
    	
    	return true;
    }
    
    /**
     * Gets the contact's given name (aka First Name)
     * 
     * @return
     */
    public String getNameGiven() { return _nameGiven; }
    
    /**
     * Sets the contact's given name
     * 
     * @param nameGiven
     * @return Whether the member was set
     */
    public boolean setNameGiven(String nameGiven)
    {
    	//TODO: Check for any injection attacks (if user provided)
    	_nameGiven = nameGiven;
    	
    	return true;
    }
    
    /**
     * Gets the contact's family name (aka Last Name)
     * 
     * @return
     */
    public String getNameFamily() { return _nameFamily; }
    
    /**
     * Sets the contact's family name
     * 
     * @param nameFamily
     * @return Whether the member was set
     */
    public boolean setNameFamily(String nameFamily)
    {
    	//TODO: Check for any injection attacks (if user provided)
    	_nameFamily = nameFamily;
    	
    	return true;
    }
    
    /**
     * Gets the contact's name from GV server
     * 
     * @return
     */
    public String getNameOnServer() { return _nameOnServer; }
    
//QUESTION: Do we need to have set, or will this be pulled directly from server in the constructor?
    
    /**
     * Gets the contact's phone number (unformatted)
     * 
     * @return
     */
    public String getPhoneNumber() { return _phoneNumber; }
    
    /**
     * Gets the contact's phone number formatted for display
     * Example: (xxx) xxx - xxxx
     * 
     * @return
     */
    public String getPhoneNumberDisplay() { return _phoneNumberDisplay; }
    
    /**
     * Gets the contact's phone type (Mobile, Home, Work, etc.)
     * 
     * @return
     */
    public PHONETYPE getPhoneType() { return _phoneType; }
    
    /**
     * Gets the uniform resource indicator for the contact's photo on the device
     * 
     * @return
     */
    public Uri getPhotoURI() { return _photoURI; }
    
    /**
     * Sets the contact's photo on the device
     * Validates the Uri provided
     * 
     * @param photoUri
     * @return Whether the Uri was valid and the member was set
     */
    public boolean setPhotoURI(Uri photoURI)
    {
    	//TODO: Validate Uri (check if correctly formatted and Uri exists)
    	_photoURI = photoURI;
    	
    	return true;
    }
    
    /**
     * Sets the contact's photo on the device from a selected file
     * 
     * @param photo
     * @return Whether the member was set
     */
    public boolean setPhotoURI(File photo)
    {
    	//TODO: Make sure file is an image
    	_photoURI = Uri.fromFile(photo);
    	
    	return true;
    }
    
    /**
     * Gets the uniform resource indicator for the contact's photo (thumbnail) on the device
     * 
     * @return
     */
    public Uri getPhotoThumbnailURI() { return _photoThumbnailURI; }
    
    /**
     * Sets the contact's photo (thumbnail) on the device
     * Validates the Uri provided
     * 
     * @param photoUri
     * @return Whether the Uri was valid and the member was set
     */
    public boolean setPhotoThumbnailURI(Uri photoThumbnailURI)
    {
    	//TODO: Validate Uri (check if correctly formatted and Uri exists)
    	_photoThumbnailURI = photoThumbnailURI;
    	
    	return true;
    }
    
    /**
     * Sets the contact's photo (thumbnail) on the device from a selected file
     * 
     * @param photo
     * @return Whether the member was set
     */
    public boolean setPhotoThumbnailURI(File photo)
    {
    	//TODO: Make sure file is an image
    	_photoThumbnailURI = Uri.fromFile(photo);
    	
    	return true;
    }
    
    /**
     * Gets the uniform resource locator for the contact's photo on GV server
     * 
     * @return
     */
    public URL getPhotoURL() { return _photoURL; }
    
}
