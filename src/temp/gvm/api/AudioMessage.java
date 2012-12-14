package temp.gvm.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Not for version 1.0, but it's always good to think ahead
 */
public class AudioMessage extends Message
{
    // TODO: store mp3 and ogg for recorded calls and voicemails
    public AudioMessage(JSONObject gvJSON) throws JSONException
    {
        super(gvJSON);
    }
}