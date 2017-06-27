/**
 *COPYRIGHT NOTICE
 *Copyright (c) 2012～2020, 19e   
 *All rights reserved.
 * @author wjf
 * @file Utilsss.java
 * @brief TODO
 * @version 
 * @date 2013-5-24 下午3:48:11
 * 
 */
package com.yqy.mvp_frame.frame.utils;

/**
 *COPYRIGHT NOTICE
 *Copyright (c) 2012～2020, 19e   
 *All rights reserved.
 * @author wjf
 * @file Utilsss.java
 * @brief TODO
 * @version 
 * @date 2013-5-24 下午3:48:11
 * 
 */
import java.util.List;
import java.util.Map;

public class MakeHfRequestUrlUtil {
	public static String getMd5(String md5Key, Map<String,String> urlMap, List<String> mdkList){
		StringBuffer md5Source = new StringBuffer("");
		if(mdkList!=null){
			for(int i=0;i<mdkList.size();i++){
				md5Source.append(mdkList.get(i));
				md5Source.append("=");
				if(urlMap==null){
					md5Source.append("");
				}else{
					md5Source.append(urlMap.get(mdkList.get(i)));
				}
				md5Source.append("&");
			}
		}
		md5Source.append("key="+md5Key);
		String str = md5Source.toString().replace("~",",");
		String md5Value = "";
		try {
			md5Value = MD5.getMd5(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5Value;
	}
}
