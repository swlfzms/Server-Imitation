package com.example.beans;

import java.io.Serializable;

public class Friend implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO����һ�仰�������������ʾʲô��
	 */

	public final long serialVersionUID = -464767809042591618L;
	public int[] friendUid;
	public String[] friendUsername;
	public  String[] friendSignature;
	public int[] friendStatus;	

	public int[] friendheadphotoversion;
	public int[] friendsignatureversion;
	// public static String[] friendIp;

	public boolean dataChanged = false;

	public Friend() {

	}

	public Friend(int[] friendUid, String[] friendUsername,
			String[] friendSignature, int[] friendStatus) {
		// TODO Auto-generated constructor stub
		this.friendUid = friendUid;
		this.friendUsername = friendUsername;
		this.friendSignature = friendSignature;
		this.friendStatus = friendStatus;
	}

}
