package com.tlc.common;

import java.util.HashMap;

import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.common.DataRow;
import com.tlc.common.DataRowCollection;
import com.tlc.common.Logger;
import com.tlc.common.Util;

public class Messanger {
	
	private static HashMap<String,HashMap<Integer,Message>> map = new HashMap<String,HashMap<Integer,Message>>();
	static{
		DataRowCollection rows = SystemInfo.getDb().QueryDataRows("SELECT ID, LANGUAGE, MESSAGE, TYPE, STATE FROM TBLMESSAGES");
		Logger.LogServer("Loading Messages...");
		for(DataRow row: rows){
			Integer id = Util.cast(row.get("ID"), int.class);
			String lang = Util.cast(row.get("LANGUAGE"), String.class); 
			Integer type = Util.cast(row.get("TYPE"), int.class); 
			String message = Util.cast(row.get("MESSAGE"));
			Integer state = Util.cast(row.get("STATE"), int.class);
			HashMap<Integer,Message> langtable = null;
			if(map.containsKey(lang))
				langtable = map.get(lang);
			else{
				langtable = new HashMap<Integer,Message>();
				map.put(lang, langtable);
			}
			if(!langtable.containsKey(id))
				langtable.put(id, new Message(id, type, message, state));
		}
	}
	
	public static Message getMessage(int id){
		return getMessage(id,"");
	}
	
	public static Message getMessage(int id, String lang, String... replaces){
		if(StringUtil.isNullOrEmpty(lang)) lang = "E";
		if(!map.containsKey(lang))
			lang = "E";
		if(map.containsKey(lang) && map.get(lang).containsKey(id)){
			Message msg = map.get(lang).get(id);
			String message = msg.getMessage();
			if(replaces !=null)
			for(int i = 0; i < replaces.length; i += 2){
				if(replaces[i] == null) continue;
				if(replaces[i+1] == null) replaces[i+1] = "";
				message = message.replace(replaces[i], replaces[i+1]);
			}
			return new Message(id, msg.getType(),message, msg.getState());
		}
		return new Message(id, 0,"msgid:" + lang + ":" + id, 1);
	}
	
	private static final String insertpndg = "INSERT INTO TBLSMPPPNDG(REFERENCEID, MSISDN, MESSAGE, TYPE, IP) VALUES (?, ?, ?, ?, ?)";
	
	public static void sendMessage(String refid, String dest, int msgid) {
		sendMessage(refid, dest, msgid, "");
	}
	
	public static void sendMessage(String refid, String dest, int msgid, String lang) {
		Message message = getMessage(msgid, lang);
		sendMessage(refid, dest, message);
	}
	
	public static void sendMessage(String refid, String dest, Message message) {
		if(message == null) return;
		sendMessage(refid, dest, message.getMessage(), message.getType());
	}
	
	public static void sendMessage(String refid, String dest, String message, int type) {
		SystemInfo.getDb().QueryUpdate(insertpndg, refid, dest, message, type, SystemInfo.getProperty(SystemInfo.PROP_HOST_IP, "127.0.0.1"));
	}

	public static class Message{
		public static final int SUCCESS = 0;
		public static final int FAILED  = 1;
		public static final int NOTE    = 2;
		private int    type;
		private String message;
		private int id;
		private int state = 1;
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Message(int id, int type, String message){
			this.id = id;
			this.type = type;
			this.message = message;
		}
		
		public Message(int id, int type, String message, int state){
			this.id = id;
			this.type = type;
			this.message = message;
			this.state = state;
		}
		
		@Override
		public Message clone() {
			try{
				return (Message)super.clone();
			}catch(Exception e){
				Logger.LogServer(e);
			}
			Message ret = new Message(id, type, message, state);
			return ret;
		}
		public int getState(){
			return state;
		}
		public int getType() {
			return type;
		}
		public Message setType(int type) {
			this.type = type;
			return this;
		}
		public String getMessage() {
			return message;
		}
		public Message setMessage(String message) {
			this.message = message;
			return this;
		}
		public Message replace(String...replaces){
			for(int i = 0; i+1 < replaces.length; i += 2){
				message = message.replace(replaces[i], replaces[i+1]);
			}
			return this;
		}
		public Message concat(String msg){
			message += msg;
			return this;
		}
	}

	public static class MessageId{
		public static final int EULA_SUCCESS                   = -2;
		public static final int UNKNOWN                        = -1;
		public static final int WELCOME_MCOM                   = 0;
		public static final int SYSTEM_BUSY                    = 1;
		public static final int SYNTAX_ERROR                   = 2;
		public static final int MSISDN_ERROR                   = 3;
		public static final int UNAUTHORISED_ERROR             = 4;
		public static final int UNAUTHORISED_SEND_ERROR        = 5;
		public static final int UNAUTHORISED_RECV_ERROR        = 6;
		public static final int LOCKED_ERROR                   = 7;
		public static final int PASSWORD_ERROR                 = 8;
		public static final int INACTIVE_ERROR                 = 9;
		public static final int INSUFFICIENT_FUND_ERROR        = 10;
		public static final int MAX_AMOUNT_SEND_ERROR          = 11;
		public static final int MIN_AMOUNT_SEND_ERROR          = 12;
		public static final int MAX_AMOUNT_SEND_DAY_ERROR      = 13;
		public static final int MAX_AMOUNT_SEND_MONTH_ERROR    = 14;
		public static final int MAX_TRANS_SEND_DAY_ERROR       = 15;
		public static final int MAX_TRANS_SEND_MONTH_ERROR     = 16;
		public static final int MAX_AMOUNT_RECV_ERROR          = 17;
		public static final int MAX_CURRENT_AMOUNT_ERROR       = 18;
		public static final int MAX_AMOUNT_RECV_DAY_ERROR      = 19;
		public static final int MAX_AMOUNT_RECV_MONTH_ERROR    = 20;
		public static final int MAX_TRANS_RECV_DAY_ERROR       = 21;
		public static final int MAX_TRANS_RECV_MONTH_ERROR     = 22;
		public static final int SELF_SEND_ERROR                = 23;
		public static final int MIN_PASSWORD_ERROR             = 24;
		public static final int MAX_PASSWORD_ERROR             = 25;
		public static final int MIN_ALIAS_ERROR                = 26;
		public static final int MAX_ALIAS_ERROR                = 27;
		public static final int ALIAS_EXISTS_ERROR             = 28;
		public static final int EMAIL_ERROR                    = 29;
		public static final int DEFAULT_PASSWORD_ERROR         = 30;
		public static final int ZERO_AMOUNT_SEND_ERROR         = 31;
		public static final int DEFAULT_NEWPASSWORD_ERROR      = 32;
		public static final int PASSWORD_LOCKED_ERROR          = 33;
		//TODO New Message IDs
		public static final int INSUFFICIENT_FUND_LOCKED_ERROR = 34;
		public static final int EXTERNAL_DATA1_ERROR           = 35;
		public static final int EXTERNAL_DATA2_ERROR           = 36;
		
		public static final int MIN_AMOUNT_RECV_ERROR          = 37;
		public static final int MIN_RAMOUNT_RECV_ERROR         = 38;
		public static final int MAX_RAMOUNT_RECV_ERROR         = 39;
		public static final int MAX_RCURRENT_AMOUNT_ERROR      = 40;
		public static final int MAX_RAMOUNT_RECV_DAY_ERROR     = 41;
		public static final int MAX_RAMOUNT_RECV_MONTH_ERROR   = 42;
		public static final int MAX_RTRANS_RECV_DAY_ERROR      = 43;
		public static final int MAX_RTRANS_RECV_MONTH_ERROR    = 44;
		public static final int REGISTER_KYC_ERROR             = 45;
		public static final int INACTIVE_DESTINATION           = 46;
		public static final int NONLOCAL_RECV_ERROR            = 51;
		public static final int MINOR_USER                     = 52;
		public static final int CBCM_MISSING_FIELD             = 53;
		public static final int CBCM_ID_ALREADY_EXISTS         = 54;
		public static final int CBCM_CANNOT_REGISTER           = 55;
		public static final int FAILED_HIERARCHY               = 56;
		public static final int USSD_PROCESSING_REQUEST        = 57;
		public static final int AIRTIME_INVALID_AMOUNT         = 58;
		
		public static final int BANK_INVALID_IBAN              = 100;
				
		public static final int KEYCOST_SENDER                       = 201;
		public static final int CR_COMMISSION_SENDER                 = 202;
		public static final int DR_COMMISSION_SENDER                 = 203;
		public static final int BONUS_SENDER                         = 204;
		public static final int KEYCOST_RECEIVER                     = 205;
		public static final int CR_COMMISSION_RECEIVER               = 206;
		public static final int DR_COMMISSION_RECEIVER               = 207;
		public static final int BONUS_RECEIVER                       = 208;
		public static final int REFERENCE1                           = 209;
		public static final int REFERENCE2                           = 210;
		
		
		public static final int NOT_IMPLEMENTED                    = 999;
		
		public static final int SUCCESS_BALANCE_INQUIRY            = 1000;
		
		public static final int SUCCESS_CASH_ORIGINATOR_           = 1001;
		public static final int SUCCESS_CASH_DESTINATION_          = 1002;
		public static final int SUCCESS_CASH_BONUS_ORIGINATOR_     = 1003;
		public static final int SUCCESS_CASH_BONUS_DESTINATION_    = 1004;
		
		public static final int SUCCESS_CHANGE_PASSWORD            = 1005;
		public static final int SUCCESS_CHANGE_ALIAS               = 1006;
		public static final int SUCCESS_NO_RECORD                  = 1007;
		public static final int SUCCESS_TRANS_RECORD               = 1008;
		public static final int SUCCESS_SUMMARY_DAY                = 1009;
		public static final int SUCCESS_REGISTER_KYC               = 1010;
		
		public static final int SUCCESS_REGISTER_MSISDN            = 1014;
		
		public static final int SUCCESS_TOPUP_ORIGINATOR           = 1015;
		public static final int SUCCESS_TOPUP_DESTINATION          = 1016;
		public static final int SUCCESS_TOPUP_BONUS_ORIGINATOR     = 1017;
		public static final int SUCCESS_TOPUP_BONUS_DESTINATION    = 1018;
		
		public static final int SUCCESS_LANGUAGE                   = 1019;
		
		public static final int SUCCESS_CASH_ORIGINATOR            = 1020;
		public static final int SUCCESS_CASH_DESTINATION           = 1021;
		public static final int SUCCESS_CASH_BONUS_ORIGINATOR      = 1022;
		public static final int SUCCESS_CASH_BONUS_DESTINATION     = 1023;
		public static final int SUCCESS_CASH_ORIGINATOR_X1         = 1024;
		public static final int SUCCESS_CASH_DESTINATION_X1        = 1025;
		public static final int SUCCESS_CASH_BONUS_ORIGINATOR_X1   = 1026;
		public static final int SUCCESS_CASH_BONUS_DESTINATION_X1  = 1027;
		public static final int SUCCESS_CASH_ORIGINATOR_X2         = 1028;
		public static final int SUCCESS_CASH_DESTINATION_X2        = 1029;
		public static final int SUCCESS_CASH_BONUS_ORIGINATOR_X2   = 1030;
		public static final int SUCCESS_CASH_BONUS_DESTINATION_X2  = 1031;
		public static final int SUCCESS_BILL_ORIGINATOR            = 1032;
		public static final int SUCCESS_BILL_DESTINATION           = 1033;
		public static final int SUCCESS_BILL_BONUS_ORIGINATOR      = 1034;
		public static final int SUCCESS_BILL_BONUS_DESTINATION     = 1035;
		public static final int SUCCESS_BILL_ORIGINATOR_X1         = 1036;
		public static final int SUCCESS_BILL_DESTINATION_X1        = 1037;
		public static final int SUCCESS_BILL_BONUS_ORIGINATOR_X1   = 1038;
		public static final int SUCCESS_BILL_BONUS_DESTINATION_X1  = 1039;
		public static final int SUCCESS_BILL_ORIGINATOR_X2         = 1040;
		public static final int SUCCESS_BILL_DESTINATION_X2        = 1041;
		public static final int SUCCESS_BILL_BONUS_ORIGINATOR_X2   = 1042;
		public static final int SUCCESS_BILL_BONUS_DESTINATION_X2  = 1043;
		public static final int SUCCESS_SHOP_ORIGINATOR            = 1044;
		public static final int SUCCESS_SHOP_DESTINATION           = 1045;
		public static final int SUCCESS_SHOP_BONUS_ORIGINATOR      = 1046;
		public static final int SUCCESS_SHOP_BONUS_DESTINATION     = 1047;
		public static final int SUCCESS_SHOP_ORIGINATOR_X1         = 1048;
		public static final int SUCCESS_SHOP_DESTINATION_X1        = 1049;
		public static final int SUCCESS_SHOP_BONUS_ORIGINATOR_X1   = 1050;
		public static final int SUCCESS_SHOP_BONUS_DESTINATION_X1  = 1051;
		public static final int SUCCESS_SHOP_ORIGINATOR_X2         = 1052;
		public static final int SUCCESS_SHOP_DESTINATION_X2        = 1053;
		public static final int SUCCESS_SHOP_BONUS_ORIGINATOR_X2   = 1054;
		public static final int SUCCESS_SHOP_BONUS_DESTINATION_X2  = 1055;
		public static final int SUCCESS_BANK_ORIGINATOR            = 1056;
		public static final int SUCCESS_BANK_DESTINATION           = 1057;
		public static final int SUCCESS_BANK_BONUS_ORIGINATOR      = 1058;
		public static final int SUCCESS_BANK_BONUS_DESTINATION     = 1059;
		public static final int SUCCESS_BANK_ORIGINATOR_X1         = 1060;
		public static final int SUCCESS_BANK_DESTINATION_X1        = 1061;
		public static final int SUCCESS_BANK_BONUS_ORIGINATOR_X1   = 1062;
		public static final int SUCCESS_BANK_BONUS_DESTINATION_X1  = 1063;
		public static final int SUCCESS_BANK_ORIGINATOR_X2         = 1064;
		public static final int SUCCESS_BANK_DESTINATION_X2        = 1065;
		public static final int SUCCESS_BANK_BONUS_ORIGINATOR_X2   = 1066;
		public static final int SUCCESS_BANK_BONUS_DESTINATION_X2  = 1067;
		
		//start email
		public static final int SUCCESS_RPRE_INPROGRESS            = 1100;

		public static final int SUCCESS_RPRE_EMAIL_SENT            = 1011;
		public static final int SUCCESS_RPRE_EMAIL_BODY            = 1012;
		public static final int SUCCESS_RPRE_EMAIL_SUBJECT         = 1013;
		
		public static final int SUCCESS_RPRE_CSV_HEADER            = 1101;
		public static final int SUCCESS_RPRE_CSV_COL_HEADER        = 1102;
		public static final int SUCCESS_RPRE_CSV_COL_RECORD        = 1103;
		public static final int SUCCESS_RPRE_CSV_TAIL              = 1104;
		public static final int SUCCESS_RPRE_CSV_FILENAME          = 1105;
		public static final int SUCCESS_RPRE_CSV_DESCRIPTION       = 1106;
		public static final int SUCCESS_MROR                       = 1107;
		
		
		
		/*gui sms messages*/
		public static final int SUCCESS_ALLOCATION                 = 2001;
		public static final int SUCCESS_APPROVED_KYC               = 2002;
		public static final int SUCCESS_LOCK                       = 2003;
		public static final int SUCCESS_UNLOCK                     = 2004;
		public static final int SUCCESS_APPROVED_USERLEVEL         = 2005;
		public static final int SUCCESS_APPROVED_SYSTEMINFO        = 2006;
		public static final int SUCCESS_APPROVED_MESSAGES          = 2007;
		public static final int SUCCESS_APPROVED_CHARGES           = 2008;
		
		// Plugging Interface Generic Message
		public static final int INTERFACE_BUSY                     = 3000;
		
		public static final int SMS_PENDING                        = 3001;
		public static final int SMS_RECEIVED                       = 3002;
	
		public static final int AIRT_FUNDAMO_BUNDLES        	   = 9001;
		public static final int REGI_FUNDAMO_MINPIN                = 9002;
		public static final int ARMW_FUNDAMO_SUCCESS        	   = 9003;
		
		/*other network messages*/
		public static final int SUCCESS_OTHER_NET_ORIG       				= 3010;
		public static final int SUCCESS_OTHER_NET_DEST     			  		= 3011;
		public static final int SUCCESS_OTHER_NET_WITHDRAW_ORIG       		= 3020;
		public static final int SUCCESS_OTHER_NET_WITHDRAW_DEST       		= 3021;
		public static final int SUCCESS_AGENT_CASHOUT_ORIG					= 3031;
		
		/*wallet 2 bank*/
		public static final int SUCCESS_REQUEST_WALLET_2_BANK				= 19000;
		
	}
}
