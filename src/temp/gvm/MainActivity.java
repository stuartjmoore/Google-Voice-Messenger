package temp.gvm;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import temp.gvm.api.Conversation;
import temp.gvm.api.Person;
import temp.gvm.api.Voice;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.AccountPicker;

public class MainActivity extends Activity
{
    private final static int                REQUEST_ACCOUNT = 1;
    protected HashMap<String, Conversation> conversation    = new HashMap<String, Conversation>();
    protected ConversationAdapater adapter = new ConversationAdapater();

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setListNavigationCallbacks(
        // Specify a SpinnerAdapter to populate the drop down list.
                new ArrayAdapter<String>(actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, getResources().getStringArray(
                                R.array.menu_main_spinner)),

                // Provide a listener to be called when an item is selected.
                new ActionBar.OnNavigationListener() {
                    public boolean onNavigationItemSelected(int position,
                            long id)
                    {
                        // Take action here, e.g. switching to the
                        // corresponding fragment.
                        return true;
                    }
                });
        this.setupVoice();
    }

    protected void setupVoice()
    {
        // Have the user select the account they want to use
        boolean hasAlreadySetupAccount = false;
        if (hasAlreadySetupAccount) {
            // TODO check for an already selected account.
            // this.doLogin(accountName);
        } else {
            try {
                // Choose a google account that has Google Voice.
                Intent accountIntent = AccountPicker.newChooseAccountIntent(
                        null, null, new String[] { "com.google" }, false, null,
                        "grandcentral",
                        new String[] { "service_grandcentral" }, null);
                this.startActivityForResult(accountIntent,
                        MainActivity.REQUEST_ACCOUNT);
                Log.i("Purple", "Starting Account Chooser Intent");
            } catch (Exception e) {
                // TODO No account support
                Log.e("Purple", "No Account Support");
            }
        }
    }

    protected void doLogin(String accountName)
    {
        // Get the Account Manager
        AccountManager manager = AccountManager.get(this);
        // Create an account object for the selected account
        Account account = new Account(accountName, "com.google");
        Log.i("Purple", "Getting Token for Grandcentral Service");
        manager.getAuthToken(account, "grandcentral", null, true,
                new TokenCallback(), null);
    }

    protected void setupLayout()
    {
        this.setContentView(R.layout.activity_main);

        ListView listView = (ListView) this.findViewById(R.id.message_list);

        listView.setAdapter(this.adapter);

        Thread thread = new Thread() {
            @Override
            public void run()
            {
                final HashMap<String, Conversation> i = MainActivity.this
                        .getVoiceInstance().getInbox();
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run()
                    {
                        MainActivity.this.updateInbox(i);
                    }
                });
            }
        };
        thread.start();
    }

    public void updateInbox(HashMap<String, Conversation> inbox)
    {
        this.conversation = inbox;
        this.adapter.notifyDataSetChanged();
        Log.i("Purple","Conversation size: " + inbox.size());
    }

    private class TokenCallback implements AccountManagerCallback<Bundle>
    {
        @Override
        public void run(AccountManagerFuture<Bundle> authToken)
        {
            String tok = null;
            Bundle result;
            try {
                result = authToken.getResult();
                tok = (String) result.get(AccountManager.KEY_AUTHTOKEN);
                if (tok != null) {
                    ((PurpleApplication) MainActivity.this.getApplication()).googleVoice = new Voice(
                            tok);
                }
                MainActivity.this.setupLayout();
            } catch (Exception e) {
                // TODO Error Checking
                e.printStackTrace();
            }
        }
    }

    private class ConversationAdapater extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return MainActivity.this.conversation.size();
        }

        @Override
        public String getItem(int position)
        {
            Set<String> keys = MainActivity.this.conversation.keySet();
            String[] sKeys = new String[this.getCount()];
            keys.toArray(sKeys);
            return sKeys[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(R.layout.thread_item, parent, false);
            }

            String id = this.getItem(position);
            Conversation convo = MainActivity.this.conversation.get(id);

            // Populate the View (this should eventually be done with tags for
            // speed)
            TextView firstName = (TextView) v
                    .findViewById(R.id.thread_contact_firstname);
            TextView lastName = (TextView) v
                    .findViewById(R.id.thread_contact_lastname);
            TextView message = (TextView) v.findViewById(R.id.thread_message);
            TextView timestamp = (TextView) v
                    .findViewById(R.id.thread_timestamp);
            QuickContactBadge badge = (QuickContactBadge) v
                    .findViewById(R.id.thread_contact_badge);
            
            Person p = convo.contacts().get(0);
            firstName.setText(p.getNameGiven());
            lastName.setText(p.getNameOnServer());
            
            // Assign the contact to the contact badge
            if(p.getID() != "-1") {
                badge.assignContactUri(p.getContactUri(MainActivity.this));
                badge.setMode(ContactsContract.QuickContact.MODE_LARGE);
                badge.setImageURI(p.getContactPhoto_Local(MainActivity.this));
            } else {
                badge.setImageResource(R.drawable.ic_contact_picture);
                badge.assignContactFromPhone(p.getPhoneNumber(), true);
            }

            return v;
        }

    }

    protected void onActivityResult(final int requestCode,
            final int resultCode, final Intent data)
    {
        if (requestCode == MainActivity.REQUEST_ACCOUNT
                && resultCode == RESULT_OK) {
            String accountName = data
                    .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            this.doLogin(accountName);
        } else {
            // TODO Error Checking
        }
    }

    private Voice getVoiceInstance()
    {
        return ((PurpleApplication) MainActivity.this.getApplication()).googleVoice;
    }
}
