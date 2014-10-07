package org.laybe.proxymate.android.idmanager;

import org.laybe.proxymate.android.services.AuthenticationService;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class SignatureService  extends Service {

	/**
	 * Command to service to get a signature.
	 */
	public static final int MSG_GET_SIGNATURE = 1;
	
	/**
	 * Handler of incoming messages from clients.
	 */
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_GET_SIGNATURE:
				Bundle data = msg.getData();
				String msgToSign = data.getString(AuthenticationService.TAG_MSG_TO_SIGN);

				String signedMessage = signMessage(msgToSign);
				try {
					Message reply = Message.obtain(null, MSG_GET_SIGNATURE, 0, 0);
					Bundle replyData = new Bundle();
					replyData.putString(AuthenticationService.TAG_SIGNED_MSG, signedMessage);
					reply.setData(replyData);
					
					msg.replyTo.send(reply);
					
				} catch (RemoteException e) {
					// TODO
				}
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger messenger = new Messenger(new IncomingHandler());

	/**
	 * When binding to the service, we return an interface to our messenger for
	 * sending messages to the service.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return messenger.getBinder();
	}

	private String signMessage(String message) {

		// TODO sign message
		return "";
	}

}