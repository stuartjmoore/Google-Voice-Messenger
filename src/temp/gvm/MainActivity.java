package temp.gvm;

import temp.gvm.api.Voice;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.AccountPicker;

public class MainActivity extends Activity
{
    private final static int REQUEST_ACCOUNT = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
                Intent accountIntent = AccountPicker.newChooseAccountIntent(null, null, new String[] { "com.google" }, false, null, "grandcentral", new String[] { "service_grandcentral" }, null);
                this.startActivityForResult(accountIntent, MainActivity.REQUEST_ACCOUNT);
            } catch (Exception e) {
                // TODO No account support
            }
        }
    }

    protected void doLogin(String accountName)
    {
        // Get the Account Manager
        AccountManager manager = AccountManager.get(this);
        // Create an account object for the selected account
        Account account = new Account(accountName, "com.google");
        manager.getAuthToken(account, "grandcentral", null, true, new TokenCallback(), null);
    }
    
    protected void setupLayout()
    {
        this.setContentView(R.layout.activity_main);
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
                    ((PurpleApplication) MainActivity.this.getApplication()).googleVoice = new Voice(tok);
                }
                MainActivity.this.setupLayout();
            } catch (Exception e) {
                // TODO Error Checking
                e.printStackTrace();
            }
        }
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        if (requestCode == MainActivity.REQUEST_ACCOUNT && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            this.doLogin(accountName);
        } else {
            // TODO Error Checking
        }
    }
}
