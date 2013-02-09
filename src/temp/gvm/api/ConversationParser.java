package temp.gvm.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.util.Log;

public class ConversationParser extends
        AsyncTask<String, Void, HashMap<String, Conversation>>
{
    private HashMap<String, Conversation> _convMerge = null;

    public ConversationParser(HashMap<String, Conversation> mergeMap)
    {
        _convMerge = mergeMap;
    }

    @Override
    protected HashMap<String, Conversation> doInBackground(String... params)
    {
        if (params.length == 0)
            return null;

        // Assume first param is the HTML
        String htmlResponse = params[0];
        HashMap<String, Conversation> ret = new HashMap<String, Conversation>();

        Document respDoc = Jsoup.parse(htmlResponse);
        List<Element> nodes = respDoc.select("div.gc-message");
        Conversation currConvo = null;

        for (Element htmlNode : nodes) {
            if (htmlNode == null)
                continue;

            currConvo = new Conversation(htmlNode);

            ret.put(currConvo.id(), currConvo);
        }

        return ret; // Once returned - we need to MERGE this with the JSON
                    // conversation based on Conversation ID
    }

    protected void onPostExecute(HashMap<String, Conversation> result)
    {
        Iterator<String> iConvKey = result.keySet().iterator();
        while (iConvKey.hasNext()) {
            String key = (String) iConvKey.next();
            if (_convMerge.containsKey(key)) {
                _convMerge.get(key).merge(result.get(key));
            } else {
                Log.w(this.getClass().getName(),
                        "Unable to merge conversation id: " + key);
            }
        }
    }
}
