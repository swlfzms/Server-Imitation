package com.example.tools;

public class SQLStatement {

	// register sql
	public final static String RegisterService_RegisterSQL = "INSERT INTO USER(username,password,email) VALUES(?,?,?)";
	public final static String RegisterService_UserIsExistOrNotSQL = "SELECT COUNT(*) FROM USER WHERE username=?;";
	public final static String RegisterService_RegisterFailureMessage = "ע��ʧ��";
	public final static String RegisterService_RegisterSuccessMessage = "ע��ɹ�";
	public final static String RegisterService_UserIsExistMessage = "�û��Ѵ���";
	public final static String RegisterService_UnknowException = "��ү,δ֪ԭ������ˣ�����";
	
	//login sql
	public final static String LoginService_LoginSQL = "SELECT id FROM USER WHERE username=? AND password=?;";
	public final static String LoginService_LoginStatusLogin = "UPDATE USER SET STATUS=1 WHERE username=?;";
	public final static String LoginService_LoginStatusLogout = "UPDATE USER SET STATUS=0 WHERE id=?;";
	public final static String LoginService_LoginIP = "UPDATE USER SET ip=? WHERE id=?";

	//publish
	//signature
	public final static String PublishSignatureService_changeSignature="UPDATE USER SET signature=? WHERE id=?";
	//addFriend
	public final static String PublishAddFriendService_addFriend="select id,status,signature,ip from user where username=?";
}
