package org.laybe.proxymate.android.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AuthDetails implements Parcelable {


	private String profileName;
	private String publicKey;
	/**
	 * The URI address of the ID Authenticator service able to authenticate the user. 
	 */
	private String idAuthenticatorURI;

	public AuthDetails(String profileName,
			String publicKey,
			String idAuthenticatorURI) {
		this.profileName = profileName;
		this.publicKey = publicKey;
		this.idAuthenticatorURI = idAuthenticatorURI;
	}

	// Parcelling part
	public AuthDetails(Parcel in){
		String[] data = new String[3];

		in.readStringArray(data);
		this.profileName = data[0];
		this.publicKey = data[1];
		this.idAuthenticatorURI = data[2];
	}


	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setIdAuthenticatorURI(String idAuthenticatorURI) {
		this.idAuthenticatorURI = idAuthenticatorURI;
	}
	
	public String getProfileName() {
		return profileName;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getIdAuthenticatorURI() {
		return idAuthenticatorURI;
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {this.profileName,
				this.publicKey,
				this.idAuthenticatorURI});
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public AuthDetails createFromParcel(Parcel in) {
			return new AuthDetails(in); 
		}

		public AuthDetails[] newArray(int size) {
			return new AuthDetails[size];
		}
	};
}
