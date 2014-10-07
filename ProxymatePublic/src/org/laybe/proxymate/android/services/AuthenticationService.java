package org.laybe.proxymate.android.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.laybe.proxymate.android.idmanager.SignatureService;
import org.laybe.proxymate.android.models.AuthDetails;
import org.laybe.proxymate.android.models.User;
import org.laybe.proxymate.android.services.modules.AuthenticationServiceModule;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class AuthenticationService extends BaseService  {

	public static final String TAG_MSG_TO_SIGN = "message_to_sign";
	public static final String TAG_SIGNED_MSG = "signed_message";

	@Inject
	SessionManager sessionManager;
	
	/**
	 * Used if the SignatureService is not ready to be called yet.
	 */
	private List<String> signaturesRequested = new ArrayList<String>(); 

	/**
	 * Used to request signature.
	 */
	private boolean signatureServiceReady = false;

	private AuthDetails authDetails;

	/**
	 * This is a mock user database, associating user UUID keys with list values:
	 * - [0] name
	 * - [1] publicKey
	 * - [2] authenticatorURI
	 * - [3] sessionId
	 */
	private Map<String,List<String>> mockUserDatabase = new HashMap<String,List<String>>(); 

	private int lastStartedId;

	public AuthenticationService() {
		super();

		// Fill mock database
		mockUserDatabase.put("123e4567-e89b-12d3-a456-426655440000",
				Arrays.asList(new String[]{"Pinouf", "ksjs9282jsk2","http://www.selves.org",""}));
		mockUserDatabase.put("493f1501-s98u-98m2-b710-02kd55440230", 
				Arrays.asList(new String[]{"Wally", "ld92k296dpqk","http://www.selves.org",""}));
		mockUserDatabase.put("635e2197-l29b-00d3-e432-556765472036",
				Arrays.asList(new String[]{"Job314", "js8q40l91nv6","http://www.selves.org",""}));
	}

	@Override
	protected List<Object> getModules() {
		return Arrays.<Object>asList(new AuthenticationServiceModule());
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		doBindToSignatureService();
	};

	@Override
	public void onDestroy() {
		doUnbindFromSignatureService();		
		super.onDestroy();
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		lastStartedId = startId;

		AuthDetails authDetails = intent.getParcelableExtra(LoginManager.INTENT_EXTRA_AUTH_DETAILS);

		authenticate(authDetails);

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	private void doBindToSignatureService() {

		// Establish a connection with the service.  We use an explicit
		// class name because there is no reason to be able to let other
		// applications replace our component.
		boundToSignatureService = bindService(new Intent(AuthenticationService.this, 
				SignatureService.class), signatureServiceConnection, Context.BIND_AUTO_CREATE);
	}

	private void doUnbindFromSignatureService() {

		if (boundToSignatureService) {
			// Detach our existing connection.
			unbindService(signatureServiceConnection);
			boundToSignatureService = false;
		}
	}

	/*
	 * Normally:
		 			- send ID Authenticator name to server
					- server requests from ID Authenticator (such as Selves Authenticator) a message to sign
					- server receives message to sign from ID Authenticator
					- server sends message to sign to client
					- send message to sign to Selves Manager
		 			- Selves Manager signs message
					- Send back signed message + public key to Proxymate
					- send them to Proxymate server which sends them to ID Authenticator
					- ID Authenticator authentifies the message and tells it to Proxymate server
					- Proxymate server grants login token to the client
	 */
	private void authenticate(AuthDetails details) {

		this.authDetails = details;

		// TODO : Send ID Authenticator to server and request message to sign.
		String mockMessageToSign = "9qjsi38fk20dc8k320:Selves";

		// Send message to sign to ID Manager
		askSignature(mockMessageToSign);
	}

	private void askSignatures(List<String> messagesToSign) {

		for (String messageToSign : messagesToSign) {
			askSignature(messageToSign);
		}
	}

	private void askSignature(String messageToSign) {

		// We do this in case the signature service has not been initialized yet.
		if (!signatureServiceReady) {
			signaturesRequested.add(messageToSign);
			return;
		}

		try {
			Message msg = Message.obtain(null,
					SignatureService.MSG_GET_SIGNATURE, this.hashCode(), 0);

			Bundle data = new Bundle();
			data.putString(TAG_MSG_TO_SIGN, messageToSign);
			msg.setData(data);
			msg.replyTo = signatureResultMessenger;

			signatureService.send(msg);

		} catch (RemoteException e) {
			// In this case the service has crashed before we could even
			// do anything with it; we can count on soon being
			// disconnected (and then reconnected if it can be restarted)
			// so there is no need to do anything here.
		}	
	}

	/** Messenger for communicating with service. */
	Messenger signatureService = null;

	/** Flag indicating whether we have called bind on the service. */
	boolean boundToSignatureService = false;


	/**
	 * Handler of incoming messages from service.
	 */
	class SignatureResultHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SignatureService.MSG_GET_SIGNATURE:

				Bundle data = msg.getData();
				String signedMessage = data.getString(TAG_SIGNED_MSG);

				doAfterMessageSigned(signedMessage);
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger signatureResultMessenger = new Messenger(new SignatureResultHandler());

	/**
	 * Class for interacting with the main interface of the service.
	 */
	private ServiceConnection signatureServiceConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className,
				IBinder service) {

			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service.  We are communicating with our
			// service through an IDL interface, so get a client-side
			// representation of that from the raw service object.
			signatureService = new Messenger(service);
			signatureServiceReady = true;
			// We do this to perform the signatures call that started before the service was ready.
			if (!signaturesRequested.isEmpty()) {
				askSignatures(signaturesRequested);
				signaturesRequested.clear();
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			signatureService = null;
		}
	};

	private void doAfterMessageSigned(String signedMessage) {

		AuthDetails authDetails = this.authDetails;
		// TODO  Send signature and authDetails to server and get user info (uuid, name, session ID) in return.
		User user = mockRetrieveUser(authDetails, signedMessage);

		sessionManager.setUser(user);

		// Stop service
		stopSelfResult(lastStartedId);
	}

	/**
	 * Normally done by server call, checking database
	 */
	private User mockRetrieveUser(AuthDetails authDetails, String signedMessage) {

		// If signedMessage is correct...
		// Check done by calling the authenticatorURI and checking the signature.

		User user = new User();

		for (String uuid : mockUserDatabase.keySet()) {
			List<String> values = mockUserDatabase.get(uuid);
			if (values.get(1).equals(authDetails.getPublicKey()) && values.get(2).equals(authDetails.getIdAuthenticatorURI())) {
				// Generate sessionId
				String sessionId = String.valueOf((int)(Math.random() * 1000));
				// Set sessionId in database
				values.set(3, sessionId);

				// Set user
				user.setName(values.get(0));
				user.setUuid(uuid);
				user.setSessionId(sessionId);
			}
		}

		return user;
	}
}
