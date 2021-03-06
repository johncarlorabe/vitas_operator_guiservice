package com.tlc.common;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil {
	private static final char base64Table[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final char hexTable[] = "0123456789ABCDEF".toCharArray();
	private static final int encodeTable[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 55, 48,
        57, 50, 49, 52, 51, 54, 53, 56, 0, 0,
        0, 0, 0, 0, 0, 87, 74, 86, 65, 71,
        77, 81, 85, 89, 84, 73, 68, 79, 88, 83,
        76, 70, 66, 80, 90, 82, 67, 69, 72, 75,
        78, 0, 0, 0, 0, 0, 0, 87, 74, 86,
        65, 71, 77, 81, 85, 89, 84, 73, 68, 79,
        88, 83, 76, 70, 66, 80, 90, 82, 67, 69,
        72, 75, 78, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0
    };
	
	private static int[]  base64Byte = new int[128];
	private static int[]  hexByte = new int[128];
	private static int[]  decodeByte = new int[128];
	static {
        for(int i=0; i< base64Table.length; i++){
        	base64Byte[base64Table[i]]= i;
        }
        for(int i=0; i< hexTable.length; i++){
        	hexByte[hexTable[i]]= i;
        }
        for(int i=0; i< encodeTable.length; i++){
        	decodeByte[encodeTable[i]]= i;
        }
    }
    public static String base64Encode(byte in[])
    {
    	StringBuilder br = new StringBuilder();
        int offset = 0;
        int len = in.length - (in.length % 3);
        int padd = (3 - in.length % 3) % 3;
        while(offset < len)
        {
        	br.append(base64Table[(in[offset] & 0xff) >> 2]);
        	br.append(base64Table[in[offset++] << 4 & 0x3f | (in[offset] & 0xff) >> 4]);
        	br.append(base64Table[in[offset++] << 2 & 0x3f | (in[offset] & 0xff) >> 6]);
        	br.append(base64Table[in[offset++] & 0x3f]);
        }
        switch(padd){
        case 1 :
        	br.append(base64Table[(in[offset] & 0xff) >> 2]);
        	br.append(base64Table[in[offset++] << 4 & 0x3f | (in[offset] & 0xff) >> 4]);
        	br.append(base64Table[in[offset++] << 2 & 0x3f]);
        	br.append("=");
        	break;
        case 2 :
        	br.append(base64Table[(in[offset] & 0xff) >> 2]);
        	br.append(base64Table[in[offset++] << 4 & 0x3f]);
        	br.append("==");
        	break;
        }
        
        return br.toString();
    }
    public static byte[] base64Decode(String in)
    {
    	ByteArrayOutputStream data = new ByteArrayOutputStream();
    	final int mask = 0xff;
    	int padd = in.endsWith( "==" ) ? 2 : in.endsWith( "=" ) ? 1 : 0;
    	int len = in.length() - (padd > 0 ? 4 : 0);
    	int i = 0;
    	while(i < len){
    		int a = base64Byte[in.charAt(i++)];
    		int b = base64Byte[in.charAt(i++)];
    		int c = base64Byte[in.charAt(i++)];
    		int d = base64Byte[in.charAt(i++)];
    		data.write((byte)(((a << 2) | (b >> 4)) & mask));
    		data.write((byte)(((b << 4) | (c >> 2)) & mask));
    		data.write((byte)(((c << 6) | d) & mask));
    	}
    	switch(padd){
    	case 1:
    		int a = base64Byte[in.charAt(i++)];
    		int b = base64Byte[in.charAt(i++)];
    		int c = base64Byte[in.charAt(i++)];
    		data.write((byte)(((a << 2) | (b >> 4)) & mask));
    		data.write((byte)(((b << 4) | (c >> 2)) & mask));
    		break;
    	case 2:
    		int e = base64Byte[in.charAt(i++)];
    		int f = base64Byte[in.charAt(i++)];
    		data.write((byte)(((e << 2) | (f >> 4)) & mask));
    		break;
    	}
        return data.toByteArray();
    }
    public static String hexEncode(byte in[])
    {
    	StringBuilder br = new StringBuilder();
        try{
        	for(byte b : in )
	        {
	        	br.append(hexTable[(b & 0xff) >> 4]);
	        	br.append(hexTable[(b & 0x0f)]);
	        }
        }catch(Exception e){}
        return br.toString();
    }
    public static byte[] hexDecode(String in)
    {
    	final int mask = 0xff;
    	ByteArrayOutputStream data = new ByteArrayOutputStream();
    	in = in.toUpperCase().replace(" ", "");
    	try{
	    	for(int i = 0; i < in.length();){
	    		int a = hexByte[in.charAt(i++)];
	    		int b = hexByte[in.charAt(i++)];
	    		data.write((byte)(((a << 4) | b) & mask));
	    	}
    	}catch(Exception e){}
        return data.toByteArray();
    }
    
    public static short[] hexDecodeShort(String in)
    {
    	final int mask = 0xffff;
    	short[] array = new short[in.length() / 4];
    	in = in.toUpperCase().replace("\\s", "");
    	for(int i = 0; i < in.length();){
    		int a = hexByte[in.charAt(i++)];
    		int b = hexByte[in.charAt(i++)];
    		int c = hexByte[in.charAt(i++)];
    		int d = hexByte[in.charAt(i++)];
    		array[i/4 - 1] = (short)(((a << 12) | (b << 8) | (c << 4) | d) & mask);
    	}
        return array;
    }
    public static String hexEncodeShort(short in[])
    {
    	StringBuilder br = new StringBuilder();
        for(short b : in )
        {
        	br.append(hexTable[(b & 0xf000) >> 12]);
        	br.append(hexTable[(b & 0xf00) >> 8]);
        	br.append(hexTable[(b & 0xf0) >> 4]);
        	br.append(hexTable[(b & 0xf)]);
        }
        return br.toString();
    }
    public static byte[] shiftEncode(byte input[])
	{
	    for(short i = 0; i < input.length; i++)
	        if(encodeTable[input[i]] != 0)
	            input[i] = (byte)encodeTable[input[i] & 0xff];
	    return input;
	}
    public static byte[] shiftDecode(byte input[])
	{
	    for(short i = 0; i < input.length; i++)
	        if(decodeByte[input[i]] != 0)
	            input[i] = (byte)decodeByte[input[i] & 0xff];
	    return input;
	}

    public static String join(String[] array,String delimiter){
    	StringBuilder str = new StringBuilder ();
    	if(delimiter==null) delimiter = "";
    	for(String data : array){
    		str.append(data);
    		str.append(delimiter);
    	}
    	if(str.length() > 0) str.delete(str.length() - delimiter.length(), str.length());
    	return str.toString();
    }
    
    public static String join(Object[] array,String delimiter){
    	StringBuilder str = new StringBuilder ();
    	if(delimiter==null) delimiter = "";
    	for(Object data : array){
    		str.append(data);
    		str.append(delimiter);
    	}
    	if(str.length() > 0) str.delete(str.length() - delimiter.length(), str.length());
    	return str.toString();
    }
    
    public static String leftPadding(String str, Integer length, char car){
    	if(length <= str.length())
    		return str.substring(str.length() - length); //Trim left
		return String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)) + str; // padd left
    }
    
	public static String rightPadding(String str, Integer length, char car) {
		if(length <= str.length())
    		return str.substring(0,length); //Trim right
    	return str +  String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)); //padd right
	}
    
	@Deprecated
    public static String LPad(String str, Integer length, char car) {
    	if(length <= str.length())
    		return str.substring(str.length() - length);
    	return str +  String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car));
    }
	
	@Deprecated
	public static String RPad(String str, Integer length, char car) {
		if(length <= str.length())
    		return str.substring(0,length);
		return String.format("%" + (length - str.length()) + "s", "").replace(" ", String.valueOf(car)) + str;
	}
    
    //public static String[] split(String data, char..delimiters){
    	
    //}
    
    public static boolean isNullOrEmpty(String value){
    	return value == null || value.length() == 0;
    }
    
    public static String nvl(String value, String defaultValue){
    	if(isNullOrEmpty(value))
    		return defaultValue;
    	return value;
    }

    public static void printGenerateKey(int len){
    	byte[] bytes = SecureRandom.getSeed(len);
		System.out.println(printBytes(bytes).replace("{name}", "key"));
    }
    
    public static String printBytes(byte[] bytes){
    	StringBuilder br = new StringBuilder("byte[] {name} = new byte[]{");
    	int counter = 0;
        for(byte b : bytes )
        {
        	if(counter == 8)
        		br.append("\r\n");
        	counter = counter % 8 ;
        	br.append("(byte) 0x");
        	br.append(hexTable[(b & 0xff) >> 4]);
        	br.append(hexTable[(b & 0x0f)]);
        	br.append(", ");
        	counter++;
        }
        br.deleteCharAt(br.length() - 1);
        br.deleteCharAt(br.length() - 1);
        br.append("};");
        return br.toString();
    }
    
    public static String unscapeHtml(String str){
    	return StringEscapeUtils.unescapeHtml4(str);
    }
    
    public static String escapeHtml(String str){
    	return StringEscapeUtils.escapeHtml4(str);
    }
    
    public static String stringToHTMLString(String string) {
    	
	    StringBuffer sb = new StringBuffer(string.length());
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++) {
	        c = string.charAt(i);

            // HTML Special Chars
            if (c == '"')
                sb.append("&quot;");
            else if (c == '&')
                sb.append("&amp;");
            else if (c == '<')
                sb.append("&lt;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '\n')
                // Handle Newline
                //sb.append("&lt;br/&gt;");
            	sb.append("&lt;br/&gt;");
            else {
                int ci = 0xffff & c;
                if (ci < 160 )
                    // nothing special only 7 Bit
                    sb.append(c);
                else {
                    // Not 7 Bit use the unicode system
                    sb.append("&#");
                    sb.append(Integer.toString(ci));
                    sb.append(';');
                    }
                }
            }
	    
	    return sb.toString();
    }
    
    public static String HTMLDecode(String sHtml)
    {
     int i;
     String sTemp;
     String[] vVar = sHtml.split("&#");
     StringBuilder Decoded=new StringBuilder();
     for(i=0;i < vVar.length ; i++)
     {
      sTemp = vVar[i];
      if (sTemp.length() > 1 && sTemp.indexOf(";")!= -1)
      {
       String strUnicode = sTemp.substring(0,sTemp.indexOf(";"));
       int value =Integer.parseInt(strUnicode);
       char unicode= (char)value;
       Decoded.append(unicode);
       Decoded.append(sTemp.substring(sTemp.indexOf(";")+1));
      }
      else
       Decoded.append(sTemp);
     }
     return Decoded.toString();
    }
    
}
