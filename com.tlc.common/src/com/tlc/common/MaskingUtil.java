package com.tlc.common;

import java.util.Arrays;
import java.util.List;

import com.tlc.common.StringUtil;
import com.tlc.common.SystemInfo;
import com.tlc.regex.NamedMatcher;

public class MaskingUtil {
	private static List<String> masks = Arrays.asList(SystemInfo.getDb().QueryArray("SELECT NAME FROM TBLMASKINGKEYS WHERE STATUS = 1", ""));
	public static void mask(StringBuilder data, NamedMatcher syntax) {
		for(String mask : masks){
			int groupIndex = syntax.groupIndex(mask);
			if(groupIndex == -1)
				continue;
			int str = syntax.start(groupIndex);
			int end = syntax.end(groupIndex);
			String masked = syntax.group(groupIndex);
			if(StringUtil.isNullOrEmpty(masked))
				continue;
			data.delete(str, end);
			data.insert(str, StringUtil.LPad("", masked.length(), '*'));

		}
	}

}
