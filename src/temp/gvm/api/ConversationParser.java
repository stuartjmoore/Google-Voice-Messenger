package temp.gvm.api;

import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;


public class ConversationParser extends AsyncTask<String, Void, HashMap<String, Conversation> >
{

    public ConversationParser()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected HashMap<String, Conversation> doInBackground(String... params)
    {
        if(params.length == 0) return null;
        
        //Assume first param is the HTML
        String htmlResponse = params[0];
        HashMap<String, Conversation> ret = new HashMap<String, Conversation>();
       
        Document respDoc = Jsoup.parse(htmlResponse);
        List<Element> nodes = respDoc.getAllElements();
        Person currPerson = null;
        Conversation currConvo = null;
        
        for(Element htmlNode : nodes) {
            if(htmlNode == null) continue;
            
            currPerson = new Person(htmlNode);
            currConvo = new Conversation(htmlNode, currPerson);
            
            ret.put(currConvo.id(), currConvo);
        }
        
        return ret; //Once returned - we need to MERGE this with the JSON conversation based on Conversation ID
    }

}
