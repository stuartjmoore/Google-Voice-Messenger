package temp.gvm.api;

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

    private String _photoURI = null; // Local device photo
    private String _photoThumbnailURI = null; // Local device photo
    private String _photoURL = null; // Server GV photo
}
