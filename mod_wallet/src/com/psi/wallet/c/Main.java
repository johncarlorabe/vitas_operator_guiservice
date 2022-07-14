package com.psi.wallet.c;

import java.text.ParseException;

import com.tlc.common.LongUtil;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long amount=null;
		try {
			amount = LongUtil.toLong("0.01");
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(amount);
		System.out.println(LongUtil.toString(amount));
	}

}
