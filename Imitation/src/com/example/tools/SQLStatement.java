package com.example.tools;

public class SQLStatement {

	// register sql
	public final static String RegisterService_RegisterSQL = "INSERT INTO USER(username,password,email,ip) VALUES(?,?,?,?)";
	public final static String RegisterService_UserIsExistOrNotSQL = "SELECT COUNT(*) FROM USER WHERE username=?;";
	public final static String RegisterService_RegisterFailureMessage = "ע��ʧ��";
	public final static String RegisterService_RegisterSuccessMessage = "ע��ɹ�";
	public final static String RegisterService_UserIsExistMessage = "�û��Ѵ���";
	public final static String RegisterService_UnknowException = "��ү,δ֪ԭ������ˣ�����";
	public final static String RegisterService_UserID = "SELECT ID FROM USER WHERE username = ?";
	
	//login sql
	public final static String LoginService_LoginSQL = "SELECT id FROM USER WHERE username=? AND password=?;";
	public final static String LoginService_LoginStatusLogin = "UPDATE USER SET STATUS=1 WHERE username=?;";
	public final static String LoginService_LoginStatusLogout = "UPDATE USER SET STATUS=0 WHERE id=?;";
	public final static String LoginService_LoginIP = "UPDATE USER SET ip=? WHERE id=?";

	//publish
	//signature
	public final static String PublishSignatureService_changeSignature="UPDATE USER SET signature=? WHERE id=?";
	public final static String PublishSignatureService_checkSignatureVersion ="select signatureversion from user where id=?";
	public final static String PublishSignatureService_changeSignatureVersion="UPDATE USER SET signatureversion=? WHERE id=?";
	//addFriend
	public final static String PublishAddFriendService_addFriend="SELECT ID,status,signature,ip FROM USER WHERE username=?";
	//frienddata;
	public final static String PublishFriendDataService_searchVersion="select signature,signatureversion,headphotoversion,phototype from user where ID in (?)";
	//headphoto
	public final static String PublishHeadPhotoService_checkheadphotoversion="select headphotoversion from user where id=?";
	public final static String PublishHeadPhotoService_updateheadphotoversion="update user set headphotoversion=?, phototype=? where id=?";
	public final static String PublishHeadPhotoService_savePath="headphoto\\";
}
