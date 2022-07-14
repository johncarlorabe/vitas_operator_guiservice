package com.tlc.bank;

import java.math.BigInteger;
import java.util.HashMap;

import com.tlc.common.StringUtil;
import com.tlc.regex.NamedMatcher;
import com.tlc.regex.NamedPattern;

public class IBAN {
	private static final HashMap<String, Integer> countrylist = new HashMap<String, Integer>();
	private static final HashMap<Character, String> charlist = new HashMap<Character,String>();
	private static final String padding = "0";	
	private static NamedPattern accountPattern = NamedPattern.compile("^((?<numeric>[0-9])|(?<alpha>[A-Z]))*$");
	private static BigInteger mod97 = new BigInteger("97");
	private static BigInteger sub98 = new BigInteger("98");
	
	static{
		countrylist.put("AL",28); // Albania
		countrylist.put("AD",24); // Andorra
		countrylist.put("AT",20); // Austria
		countrylist.put("AZ",28); // Azerbaijan
		countrylist.put("BE",16); // Belgium
		countrylist.put("BH",22); // Bahrain
		countrylist.put("BA",20); // Bosnia and Herzegovina
		countrylist.put("BG",22); // Bulgaria
		countrylist.put("CR",21); // Costa Rica
		countrylist.put("HR",21); // Croatia
		countrylist.put("CY",28); // Cyprus
		countrylist.put("CZ",24); // Czech Republic
		countrylist.put("DK",18); // Denmark
		countrylist.put("DO",28); // Dominican Republic
		countrylist.put("EE",20); // Estonia
		countrylist.put("FO",18); // Faroe Islands
		countrylist.put("FI",18); // Finland
		countrylist.put("FR",27); // France
		countrylist.put("GE",22); // Georgia
		countrylist.put("DE",22); // Germany
		countrylist.put("GI",23); // Gibraltar
		countrylist.put("GR",27); // Greece
		countrylist.put("GL",18); // Greenland
		countrylist.put("GT",28); // Guatemala
		countrylist.put("HU",28); // Hungary
		countrylist.put("IS",26); // Iceland
		countrylist.put("IE",22); // Ireland
		countrylist.put("IL",23); // Israel
		countrylist.put("IT",27); // Italy
		countrylist.put("KZ",20); // Kazakhstan
		countrylist.put("KW",30); // Kuwait
		countrylist.put("LV",21); // Latvia
		countrylist.put("LB",28); // Lebanon
		countrylist.put("LI",21); // Liechtenstein
		countrylist.put("LT",20); // Lithuania
		countrylist.put("LU",20); // Luxembourg
		countrylist.put("MK",19); // Macedonia
		countrylist.put("MT",31); // Malta
		countrylist.put("MR",27); // Mauritania
		countrylist.put("MU",30); // Mauritius
		countrylist.put("MC",27); // Monaco
		countrylist.put("MD",24); // Moldova
		countrylist.put("ME",22); // Montenegro
		countrylist.put("NL",18); // Netherlands
		countrylist.put("NO",15); // Norway
		countrylist.put("PK",24); // Pakistan
		countrylist.put("PS",29); // Palestinian Territory
		countrylist.put("PL",28); // Poland
		countrylist.put("PT",25); // Portugal
		countrylist.put("RO",24); // Romania
		countrylist.put("SM",27); // San Marino
		countrylist.put("SA",24); // Saudi Arabia
		countrylist.put("RS",22); // Serbia
		countrylist.put("SK",24); // Slovakia
		countrylist.put("SI",19); // Slovenia
		countrylist.put("ES",24); // Spain
		countrylist.put("SE",24); // Sweden
		countrylist.put("CH",21); // Switzerland
		countrylist.put("TN",24); // Tunisia
		countrylist.put("TR",26); // Turkey
		countrylist.put("AE",23); // United Arab Emirates
		countrylist.put("GB",22); // United Kingdom
		countrylist.put("VG",24); // Virgin Islands, British

		charlist.put('0', "0");
		charlist.put('1', "1");
		charlist.put('2', "2");
		charlist.put('3', "3");
		charlist.put('4', "4");
		charlist.put('5', "5");
		charlist.put('6', "6");
		charlist.put('7', "7");
		charlist.put('8', "8");
		charlist.put('9', "9");
		charlist.put('A', "10");
		charlist.put('B', "11");
		charlist.put('C', "12");
		charlist.put('D', "13");
		charlist.put('E', "14");
		charlist.put('F', "15");
		charlist.put('G', "16");
		charlist.put('H', "17");
		charlist.put('I', "18");
		charlist.put('J', "19");
		charlist.put('K', "20");
		charlist.put('L', "21");
		charlist.put('M', "22");
		charlist.put('N', "23");
		charlist.put('O', "24");
		charlist.put('P', "25");
		charlist.put('Q', "26");
		charlist.put('R', "27");
		charlist.put('S', "28");
		charlist.put('T', "29");
		charlist.put('U', "30");
		charlist.put('V', "31");
		charlist.put('W', "32");
		charlist.put('X', "33");
		charlist.put('Y', "34");
		charlist.put('Z', "35");
	}

	
	public static String generateIBAN(String country, String accountnumber){
		if(StringUtil.isNullOrEmpty(country))
			throw new IllegalArgumentException("null or empty country");
		if(StringUtil.isNullOrEmpty(accountnumber))
			throw new IllegalArgumentException("null or empty accountnumber");
		country = country.toUpperCase();
		accountnumber = accountnumber.toUpperCase();
		
		StringBuilder iban = new StringBuilder(accountnumber.toUpperCase());
		if(!countrylist.containsKey(country))
			throw new IllegalArgumentException("country doesn't exists");
		int countrylen = countrylist.get(country);
		if(iban.length() + 4 > countrylen)
			throw new IllegalArgumentException("accountnumber is too long");
		iban.append(country).append("00");
		NamedMatcher match = accountPattern.matcher(iban.toString());
		if(!match.matches())
			throw new IllegalArgumentException("invalid character in accountnumber.");
		int len = iban.length();
		for(int i = 0; i < len; i++)
			iban.append(charlist.get(iban.charAt(i)));

		//clear old iban
		iban.delete(0, len);
		
		String checkdigit = sub98.subtract(new BigInteger(iban.toString()).mod(mod97)).toString();
		if(checkdigit.length() == 1) checkdigit = "0" + checkdigit;
		
		iban.delete(0, iban.length()); //clear all
		
		iban.append(country).append(checkdigit);
		for(;len < countrylen; len++)
			iban.append(padding);
		iban.append(accountnumber);
		return iban.toString();
	}
	
	public static boolean isValidIBAN(String iban){
		StringBuilder str = new StringBuilder(iban);
		String country = str.substring(0, 2);
		if(!countrylist.containsKey(country))
			return false;

		if(countrylist.get(country) != iban.length())
			return false;
		
		str.append(str.substring(0, 4)).delete(0, 4);
		
		int len = str.length();
		for(int i = 0; i < len; i++)
			str.append(charlist.get(str.charAt(i)));
		//clear old iban
		str.delete(0, len);
		return new BigInteger(str.toString()).mod(mod97).equals(BigInteger.ONE);
	}
}
