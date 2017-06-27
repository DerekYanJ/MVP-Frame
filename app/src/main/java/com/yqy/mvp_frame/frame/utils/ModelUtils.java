package com.yqy.mvp_frame.frame.utils;

import android.graphics.Color;
import android.text.TextUtils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *COPYRIGHT NOTICE
 *Copyright (c) 2012～2020, 19e   
 *All rights reserved.
 * @author wjf
 * @file ModelUtils.java
 * @brief TODO
 * @version 
 * @date 2012-10-18
 * 
 */
public class ModelUtils {

    /**
     * 是否为纯数字
     * @param str
     * @return
     */
    public static boolean isNumer(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
	 *
	 * 必须同时包含大小写字母及数字
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isSearch(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
		boolean isUpperCase = false;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLowerCase = true;
			} else if (Character.isUpperCase(str.charAt(i))) {
				isUpperCase = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
		return isRight;
	}
	/**
	 * 获取编码后的String
	 * @param str
	 * @return
	 */
	public static String getEncodeString(String str){
		try {
			str = URLEncoder.encode(str.trim(),"utf-8");
			str = str.replace("+","%2B");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取解码后的String
	 * @param str
	 * @return
	 */
	public static String getDecoderString(String str){
		try {
			str = URLDecoder.decode(str.replace("%2B","+"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 判断密码是否由两种或两种以上的符号类型组成其中数字、小写字母、大写字母、特殊符号
	 * 分别各算一类
	 * 
	 * @author liujx
	 * @param password 待判断的字符串，此函数主要用于密码判定
	 * @return true 标示符合要求，false为不符合要求
	 * */
/*	public static boolean hasTwoOrMoreTypeChar(String password){
		if(password.length() < 8)return false;
		int numFlag = 0;
		int smallCharFlag = 0;
		int bigCharFlag = 0;
		int elseFlag = 0;
		for(int i = 0; i < password.length() ; i++){
			char temp = password.charAt(i);
			if(temp <= '9' && temp >= '0'){
				numFlag = 1;
			}else if(temp <= 'z' && temp >= 'a'){
				smallCharFlag = 1;
			}else if(temp <= 'Z' && temp >= 'A' ){
				bigCharFlag = 1;
			}else{
				elseFlag = 1;
			}
			if(numFlag + smallCharFlag + bigCharFlag + elseFlag >= 2)
				return true;
		}
		return false;
	}*/


	/*public static boolean hasTwoOrMoreTypeChar(String password){
		boolean tip = true;

		Pattern p = Pattern.compile("^.*(.)\\1{3}.*$");

		if (TextUtils.isEmpty(password)) {
			tip = false;
		}else if (password.length() < 6) {
			tip = false;
		}else if(p.matcher(password).matches()){
			tip = false;
		}
		return tip;
	}*/
	public static boolean hasTwoOrMoreTypeChar(String password){
		boolean tip = true;

		Pattern p = Pattern.compile("^(?:([0-9])\\1{5})$");
		Pattern p2 = Pattern.compile("^.*(.)\\1{5}.*$|(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){5}" +
				"|(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){5})\\d");

		if (TextUtils.isEmpty(password)) {
			tip = false;
		}else if (password.length() < 6) {
			tip = false;
		}else if(p.matcher(password).matches()){
			tip = false;
		}else if (p2.matcher(password).matches()) {
			tip = false;
		}

		return tip;
	}


	/**
	 * 判断一个字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean hasLength(String s){
		return (s != null && s.length() > 0);
	}
	
	/**
	 * check tel number rule
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		// Pattern p =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9]))\\d{8}$");
		Pattern p = Pattern
				.compile("^1(3[\\d]|4[57]|5[012356789]|8[0123456789]|7[0123456789])\\d{8}$");
		Matcher m = p.matcher(mobiles);
		// System.out.println(m.matches()+"---");
		return m.matches();
	}
	
	/**
	 * 
	 * @author wjf
	 * @brief string turn integer
	 * @version 
	 * @date 2012-12-21
	 * @param value
	 * @return
	 */
	public static int StringConverToInt(String value){
		try{
			return Integer.parseInt(value);
		}catch(Exception e){
//			e.printStackTrace();
		}
		return 0;
	}
	public static int StringConverToColor(String value){
		try{
			return Color.parseColor(value);
		}catch (Exception e) {
//			e.printStackTrace();
		}
		return Color.parseColor("#000000");
	}
	/**
	 * 
	 * @author wjf
	 * @brief string turn long
	 * @version 
	 * @date 2013-1-29
	 * @param value
	 * @return
	 */
	public static long StringConverToLong(String value){
		try{
			return Long.parseLong(value);
		}catch(Exception e){
//			e.printStackTrace();
		}
		return 0;
	}
	public static double StringConverToDouble(String value){
		try{
			return Double.parseDouble(value);
		}catch(Exception e){
//			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 
	 * @author wjf
	 * @brief string turn boolean
	 * @version 
	 * @date 2012-12-21
	 * @param value
	 * @return
	 */
	public static boolean StringConverToBoolean(String value){
		try{
			return Boolean.parseBoolean(value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @author wjf
	 * @brief 节点上的属性
	 * @version 
	 * @date 2012-11-6
	 * @param node
	 * @return
	 */
	public static Map<String,String> parserAttribute(Node node){
		Map<String,String> attribute = new HashMap<String,String>();
		NamedNodeMap nnm = node.getAttributes();
		for (int j = 0; nnm != null && j < nnm.getLength(); j++) {
			Node nod1e = nnm.item(j);
			attribute.put(nod1e.getNodeName(),nod1e.getNodeValue());
			
		}
		return attribute;
	}

	/**
	 * 判断当前输入的邮箱是否合法，判断当前输入的是否为邮箱
	 * @return
	 */
	public static boolean isEmailValid(String str) {
		Pattern p = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		Matcher m = p.matcher(str);
		boolean isValid = m.matches();
		return isValid;
	}

	/**
	 * 判断当前输入的手机号是否合法，判断当前输入的是否为手机号
	 * @return
	 */
	public static boolean isPhoneNumValid(String str) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		boolean isValid = m.matches();
		return isValid;
	}

	/**
	 * 获取当前日期的格式 格式：2012-05-10
	 * @return
	 */
	public static String getCurrentDateYMD(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}
	public static String getCurrentDateYMDByFormat(String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}
	/**
	 * 
	 * @author wjf
	 * @brief 匹配正则表达式
	 * @version 
	 * @date 2012-12-18
	 * @param input
	 * @param regex
	 * @return
	 */
	public static boolean compile(String input, String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		return m.matches();
	}

	/*public static String formatValue(String value, FormatType formatType) {
		switch (formatType) {
			case defaultFormat:
				break;
			case MD5:
				try {
					value = MD5.getMd5(value, ConstUtils.ChartSet.UTF8);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case phone:
				value = value.replaceAll(" ", "");
				break;
			case bankCard:
				value = value.replaceAll(" ", "");
				break;
			case safePhone:
				value = value.substring(0, 3) + "****" + value.substring(value.length() - 4, value.length());
				break;
			case time1:
				if(value.equals("SYSTEMTIME")){
					value = ModelUtils.getCurrentDateYMDByFormat("yyyyMMddHHmmss");
				}
				break;
			default:
				break;
		}
		return value;
	}*/
	
	/**
     * 固话号码显示格式.
     * @param telNumber String
     * @param areaNumber String
     * @return String
     */
    public static String getFixNumber(String areaNumber, String telNumber) {
        if (areaNumber == null || "".equals(areaNumber) || telNumber == null
                || "".equals(telNumber)) {
            return "";
        }
        String number = "";
        final String s1 = telNumber.substring(0, 4);
        final String s2 = telNumber.substring(4);
        number = areaNumber + "-" + s1 + "-" + s2;
        return number;
        
    };
    /** 
     * 手机号码显示格式.
     * @param number String
     * @return String
     */
    public static String getNewNumber(String number) {
        if (number == null || "".equals(number)) {
            return "";
        }
        final String s1 = number.substring(0, 3);
        final String s2 = number.substring(3, 7);
        final String s3 = number.substring(7, 11);
        number = s1 + "-" + s2 + "-" + s3;
        return number;

    };
    /** 
     * 手机号码显示格式1.
     * @param number String
     * @return String
     */
    public static String getNumber(String number) {
        if (number == null || "".equals(number)) {
            return "";
        }
        String s1 = "";
        String s2 = "";
        String s3 = "";
        
        int length = number.length();
        
        if (length >= 3) {
            s1 = number.substring(0, 3);
        }
        if (length >= 7) {
            s2 = number.substring(3, 7);
            if (length >= 11) {
                s3 = number.substring(7, 11);
            } else {
                s3 = number.substring(7, length);
            }
        } else {
            s2 = number.substring(3, length);
        }
        number = s1 + " " + s2 + " " + s3;
        return number;

    };
    
    /** 
     * 
     * 计算地球上任意两点(经纬度)距离 
     * @author jinsc
     * @return 返回距离 单位：米 
     */  
    private static double EARTH_RADIUS = 6378137.0;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
    public static double DistanceOfTwoPoints(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
}
