package temp.gvm.api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.provider.*;

/**
 * A contact
 * 
 * Needs to be linked with the user's contact list.
 */
public class Person
{
    private final class HTMLQuery
    {
        public static final String Name        = "a.gc-under";                 // Not
                                                                                // sure
                                                                                // if
                                                                                // this
                                                                                // is
                                                                                // right
                                                                                // search
        public static final String Id          = "span.gc-message-contact-id";
        public static final String PhoneNumber = "span.gc-message-type";
        public static final String PortraitURL = "div.gc-message-portrait img";
    }

    private final class JSONKeys
    {
        public static final String PhoneNumber        = "phoneNumber";
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
     * 
     * @param htmlNode
     */
    public Person(Element htmlNode)
    {
        _nameOnServer = htmlNode.select(HTMLQuery.Name).first().text();
        this.setName(_nameOnServer);
        try {
            _id = htmlNode.select(HTMLQuery.Id).first().text();
        } catch (Exception e) {
            _id = "-1";
        }
        try {
            _photoURL = new URL("http://google.com/"
                    + htmlNode.select(HTMLQuery.PortraitURL).first()
                            .attr("src"));
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
            _photoURL = null;
        }

        Element phoneNumElement = htmlNode.select(HTMLQuery.PhoneNumber)
                .first();
        if (phoneNumElement != null) {
            _phoneNumberDisplay = phoneNumElement.text();
            _phoneNumber = getRawPhoneNumber(_phoneNumberDisplay);
        }
    }

    public Person(JSONObject jsonPerson) throws JSONException
    {
        _phoneNumber = jsonPerson.getString(JSONKeys.PhoneNumber);
        _phoneNumberDisplay = jsonPerson.getString(JSONKeys.PhoneNumberDisplay);
    }

    // GV object ID
    private String    _id                 = null;

    // Key to connect GV contact to device list
    private String    _lookupKey          = null;

    private String    _nameDisplay        = null;

    // From Contacts database, may be needed for
    private String    _nameGiven          = null;

    // UI bolding and wording
    private String    _nameFamily         = null;

    private String    _nameOnServer       = null;

    private String    _phoneNumber        = null;

    private String    _phoneNumberDisplay = null;

    // 7: mobile (from GV)
    private PHONETYPE _phoneType          = PHONETYPE.NOTINIT;

    // Local device photo
    private Uri       _photoURI           = null;

    // Local device photo
    private Uri       _photoThumbnailURI  = null;

    // Server GV photo
    private URL       _photoURL           = null;

    private String getRawPhoneNumber(String formattedNumber)
    {
        // Regex replace comma, parens, plus and space
        return formattedNumber.replaceAll("[a-z-,\\(\\)\\+ ]", "");
    }

    /**
     * Gets the GV server object ID
     * 
     * @return
     */
    public String getID()
    {
        return _id;
    }

    /**
     * Gets the key to connect device contact to GV contact
     * 
     * @return
     */
    public String getLookupKey()
    {
        return _lookupKey;
    }

    /**
     * Sets the key to connect device contact to GV contact
     * 
     * @param lookupKey
     * @return Whether key is valid and member was set
     */
    public boolean setLookupKey(String lookupKey)
    {
        // TODO: Verify lookup key exists
        // TODO: Check for any injection attacks (if user provided)
        _lookupKey = lookupKey;

        return true;
    }

    /**
     * Gets the name of the contact to display
     * 
     * @return
     */
    public String getNameDisplay()
    {
        return _nameDisplay;
    }

    /**
     * Sets the contact's display name
     * 
     * @param nameDisplay
     * @return Whether the member was set
     */
    public boolean setNameDisplay(String nameDisplay)
    {
        // TODO: Check for any injection attacks (if user provided)
        _nameDisplay = nameDisplay;

        return true;
    }

    /**
     * Gets the contact's given name (aka First Name)
     * 
     * @return
     */
    public String getNameGiven()
    {
        return _nameGiven;
    }

    /**
     * Sets the contact's given name
     * 
     * @param nameGiven
     * @return Whether the member was set
     */
    public boolean setNameGiven(String nameGiven)
    {
        // TODO: Check for any injection attacks (if user provided)
        _nameGiven = nameGiven;

        return true;
    }

    /**
     * Gets the contact's family name (aka Last Name)
     * 
     * @return
     */
    public String getNameFamily()
    {
        return _nameFamily;
    }

    /**
     * Sets the contact's family name
     * 
     * @param nameFamily
     * @return Whether the member was set
     */
    public boolean setNameFamily(String nameFamily)
    {
        // TODO: Check for any injection attacks (if user provided)
        _nameFamily = nameFamily;

        return true;
    }

    /**
     * Gets the contact's name from GV server
     * 
     * @return
     */
    public String getNameOnServer()
    {
        return _nameOnServer;
    }
    
    /**
     * Parses the first and last name out of a string
     * 
     * @param nameFull
     * @return
     */
    public boolean setName(String nameFull)
    {
        if(nameFull.contains(" ")) {
            int pos = nameFull.indexOf(" ");
            this.setNameGiven(nameFull.substring(0, pos));
            this.setNameFamily(nameFull.substring(pos+1));
        } else {
            this.setNameGiven(nameFull);
        }
        return true;
    }

    // QUESTION: Do we need to have set, or will this be pulled directly from
    // server in the constructor?

    /**
     * Gets the contact's phone number (unformatted)
     * 
     * @return
     */
    public String getPhoneNumber()
    {
        return _phoneNumber;
    }

    /**
     * Gets the contact's phone number formatted for display Example: (xxx) xxx
     * - xxxx
     * 
     * @return
     */
    public String getPhoneNumberDisplay()
    {
        return _phoneNumberDisplay;
    }

    /**
     * Gets the contact's phone type (Mobile, Home, Work, etc.)
     * 
     * @return
     */
    public PHONETYPE getPhoneType()
    {
        return _phoneType;
    }

    /**
     * Gets the uniform resource indicator for the contact's photo on the device
     * 
     * @return
     */
    public Uri getPhotoURI()
    {
        return _photoURI;
    }

    /**
     * Sets the contact's photo on the device Validates the Uri provided
     * 
     * @param photoUri
     * @return Whether the Uri was valid and the member was set
     */
    public boolean setPhotoURI(Uri photoURI)
    {
        // TODO: Validate Uri (check if correctly formatted and Uri exists)
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
        // TODO: Make sure file is an image
        _photoURI = Uri.fromFile(photo);

        return true;
    }

    /**
     * Gets the uniform resource indicator for the contact's photo (thumbnail)
     * on the device
     * 
     * @return
     */
    public Uri getPhotoThumbnailURI()
    {
        return _photoThumbnailURI;
    }

    /**
     * Sets the contact's photo (thumbnail) on the device Validates the Uri
     * provided
     * 
     * @param photoUri
     * @return Whether the Uri was valid and the member was set
     */
    public boolean setPhotoThumbnailURI(Uri photoThumbnailURI)
    {
        // TODO: Validate Uri (check if correctly formatted and Uri exists)
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
        // TODO: Make sure file is an image
        _photoThumbnailURI = Uri.fromFile(photo);

        return true;
    }

    /**
     * Gets the uniform resource locator for the contact's photo on GV server
     * 
     * @return
     */
    public URL getPhotoURL()
    {
        return _photoURL;
    }

    /**
     * Gets the URI for the contact's photo
     * 
     * @param context
     * @return Uri of the contact's photo
     */
    public Uri getContactPhoto_Local(Context context)
    {
        final String[] projURI = new String[] { ContactsContract.Contacts.PHOTO_URI };
        final Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
                Uri.encode(_phoneNumber));
        final Cursor cursor = context.getContentResolver().query(uri, projURI,
                null, null, null);

        try {
            String ret = null;
            if (cursor.moveToFirst()) {
                ret = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
            }
            if(ret == null) return null;
            return Uri.parse(ret);
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            return null;
        } finally {
            cursor.close();
        }
    }

    public Uri getContactUri(Context context)
    {
        // TODO return a lookup URI if the person is not yet a contact
        // e.g. a phone number with no contact information
        final String[] projURI = new String[] { ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY };
        final Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
                Uri.encode(_phoneNumber));
        final Cursor cursor = context.getContentResolver().query(uri, projURI,
                null, null, null);

        ContentValues values = new ContentValues();

        try {
            Uri ret = null;
            if (cursor.moveToFirst()) {
                long id = cursor.getLong(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String lookup = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                ret = ContactsContract.Contacts.getLookupUri(id, lookup);
            }
            return ret;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            return null;
        } finally {
            cursor.close();
        }
    }
}
