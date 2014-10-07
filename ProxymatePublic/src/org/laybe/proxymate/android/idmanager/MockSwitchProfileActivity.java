package org.laybe.proxymate.android.idmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.laybe.proxymate.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * Normally handled by third party ID manager application
 */
public class MockSwitchProfileActivity extends Activity {

	private static final Map<String, String> profilesPublicKeys = new HashMap<String, String>();

	private static final String ID_AUTHENTICATOR = "http://www.selves.org";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch_profile);

		final ListView profileListView = (ListView) findViewById(R.id.switchProfileListView); 

		profilesPublicKeys.put("Pinouf", "ksjs9282jsk2");
		profilesPublicKeys.put("Wally", "ld92k296dpqk");
		profilesPublicKeys.put("Job314", "js8q40l91nv6");
		profilesPublicKeys.put("Mimi34", "p6ge3vtr5s32");    

		List<String> profileList = new ArrayList<String>(profilesPublicKeys.keySet());  

		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_item_profile, profileList);  

		profileListView.setAdapter(listAdapter);  

		profileListView.setClickable(true);
		profileListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				String profileClicked = (String) profileListView.getItemAtPosition(position);
				doProfileSelected(profileClicked);
			}
		});
	}

	private void doProfileSelected(String profileSelected) {

		/*
		 * NORMAL PROCESS
		 * 

		 Ask Selves Manager (or other profiles manager) to display profiles chooser dialog

		 If user cancel
		 			- stay logged in with previous account
		 If user create new profile
					- create profile in Selves Manager
					- select profile
		 If user select profile
					- Selves Manager sends Proxymate which ID Authenticator it is using
		 			- Proxymate sends ID Authenticator name to Proxymate server
					- Proxymate server requests from ID Authenticator (such as Selves Authenticator) a message to sign
					- Proxymate server receives message to sign from ID Authenticator
					- Proxymate server sends message to sign to client
					- Proxymate sends message to sign to Selves Manager
		 			- Selves Manager signs message
					- Send back signed message + public key to Proxymate
					- Proxymate sends them to Proxymate server which sends them to ID Authenticator
					- ID Authenticator authentifies the message and tells it to Proxymate server
					- Proxymate server grants login token to the client

		 */

		/*
		 * SIMPLIFIED PROCESS (for demo)
		 * 

		 Proxymate displays profiles chooser dialog (from local list of profiles)
		 If user cancel
		 			- stay logged in with previous account
		 If user create new profile
					- add profile to local list
					- select profile
		 If user select profile
		 			- grant authentification

		 */

		Intent returnIntent = new Intent();
		returnIntent.putExtra("profileSelected", profileSelected);
		returnIntent.putExtra("publicKey", profilesPublicKeys.get(profileSelected));
		returnIntent.putExtra("idAuthenticator", ID_AUTHENTICATOR);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	private void doCancel() {
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);
		finish();
	}
}
