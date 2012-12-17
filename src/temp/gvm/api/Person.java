package temp.gvm.api;

import java.io.File;
import java.net.URL;

import org.json.JSONObject;

import android.net.Uri;
//import android.net.URL; //Why is this not working? http://developer.android.com/reference/java/net/URL.html

/**
 * A contact
 * 
 * Needs to be linked with the user's contact list.
 */
public class Person
{
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
    private URL _photoURL = null; // Server GV photo TODO: make URL object
    
    
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
