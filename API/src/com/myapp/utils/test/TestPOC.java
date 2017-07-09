package com.myapp.utils.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kobjects.base64.Base64;

public class TestPOC {
	public static final String POCURL = "http://10.122.22.118:8000/Sale_Sourcing/service/getSourcing.xsjs";
	/**
	 * send post request
	 * 
	 */
	public static String sendPocPost(String param) {
		BufferedReader reader = null;
		String result = "";
		try {
			String realurl = TestPOC.POCURL;

			URL url = new URL(realurl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String author = "Basic " + Base64.encode(("BIANZH1" + ":" + "Initial1").getBytes());

			conn.setRequestProperty("Authorization", author);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			
			// these settings are needed for post request
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			// connect server
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// send request,param is the content
			out.writeBytes(param);
			// flush Stream
			out.flush();

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}
            // disconnect
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("send POST request exception!" + e);
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * send get request
	 * 
	 */
	public static String sendPocGet(String param) {
		BufferedReader reader = null;
		String result = "";
		try {
			String urlbase = TestPOC.POCURL;
			String realurl = StringUtils.isEmpty(param) ? urlbase : urlbase + "?" + param;
			URL url = new URL(realurl);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String author = "Basic " + Base64.encode(("BIANZH1" + ":" + "Initial1").getBytes());
			
			conn.setRequestProperty("Authorization", author);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestMethod("GET");
			
			conn.connect();
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}
            // disconnect
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("send GET request exception!" + e);
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * packageData
	 * 
	 */
	public static String packageData(){
		JSONObject obj1 = new JSONObject();
		obj1.put("ZGROU", "1");
		obj1.put("POSNR", "10");
		obj1.put("VKORG", "US31");
		obj1.put("AUART", "YBOR");
		obj1.put("LAND1", "US");
		obj1.put("VKBUR", "US21");
		obj1.put("MATNR", "U01T3C1");
		obj1.put("VSBED", "L4");
		obj1.put("MVGR1", "XHS");
		obj1.put("ZFAMIL", "");
		obj1.put("ZMACTY", "9532");
		obj1.put("ZZGEO", "MG");
		obj1.put("ZZSGEO", "NA");
		obj1.put("ZSREG", "");
		obj1.put("MTPOS", "YSBN");
		obj1.put("KUNNR", "1213415451");
		obj1.put("KUNAG", "1213415451");
		obj1.put("VTWEG", "21");
		obj1.put("AUTLF", "");
		obj1.put("GRKOR", "");
		
		JSONObject obj2 = new JSONObject();
		obj2.put("ZGROU", "1");
		obj2.put("POSNR", "20");
		obj2.put("VKORG", "US31");
		obj2.put("AUART", "YBOR");
		obj2.put("LAND1", "US");
		obj2.put("VKBUR", "US21");
		obj2.put("MATNR", "00Y3450");
		obj2.put("VSBED", "L4");
		obj2.put("MVGR1", "");
		obj2.put("ZFAMIL", "");
		obj2.put("ZMACTY", "00Y3");
		obj2.put("ZZGEO", "MG");
		obj2.put("ZZSGEO", "NA");
		obj2.put("ZSREG", "");
		obj2.put("MTPOS", "YSSL");
		obj2.put("KUNNR", "1213415451");
		obj2.put("KUNAG", "1213415451");
		obj2.put("VTWEG", "21");
		obj2.put("AUTLF", "");
		obj2.put("GRKOR", "");
		
		JSONObject obj3 = new JSONObject();
		obj3.put("ZGROU", "1");
		obj3.put("POSNR", "30");
		obj3.put("VKORG", "US31");
		obj3.put("AUART", "YBOR");
		obj3.put("LAND1", "US");
		obj3.put("VKBUR", "US21");
		obj3.put("MATNR", "9532AC1");
		obj3.put("VSBED", "L4");
		obj3.put("MVGR1", "XHS");
		obj3.put("ZFAMIL", "");
		obj3.put("ZMACTY", "9532");
		obj3.put("ZZGEO", "MG");
		obj3.put("ZZSGEO", "NA");
		obj3.put("ZSREG", "");
		obj3.put("MTPOS", "YSCB");
		obj3.put("KUNNR", "1213415451");
		obj3.put("KUNAG", "1213415451");
		obj3.put("VTWEG", "21");
		obj3.put("AUTLF", "");
		obj3.put("GRKOR", "");
		
		JSONObject obj4 = new JSONObject();
		obj4.put("ZGROU", "2");
		obj4.put("POSNR", "10");
		obj4.put("VKORG", "DK10");
		obj4.put("AUART", "YBOR");
		obj4.put("LAND1", "DK");
		obj4.put("VKBUR", "DK20");
		obj4.put("MATNR", "20AWA08F00");
		obj4.put("VSBED", "L2");
		obj4.put("MVGR1", "MOB");
		obj4.put("ZFAMIL", "440P");
		obj4.put("ZMACTY", "20AW");
		obj4.put("ZZGEO", "MG");
		obj4.put("ZZSGEO", "WE");
		obj4.put("ZSREG", "NORTH");
		obj4.put("MTPOS", "BANC");
		obj4.put("KUNNR", "1213058450");
		obj4.put("KUNAG", "1213058450");
		obj4.put("VTWEG", "11");
		obj4.put("AUTLF", "X");
		obj4.put("GRKOR", "");
		
		JSONObject obj5 = new JSONObject();
		obj5.put("ZGROU", "2");
		obj5.put("POSNR", "20");
		obj5.put("VKORG", "DK10");
		obj5.put("AUART", "YBOR");
		obj5.put("LAND1", "DK");
		obj5.put("VKBUR", "DK20");
		obj5.put("MATNR", "4X20E53341");
		obj5.put("VSBED", "L2");
		obj5.put("MVGR1", "OPT");
		obj5.put("ZFAMIL", "");
		obj5.put("ZMACTY", "401");
		obj5.put("ZZGEO", "MG");
		obj5.put("ZZSGEO", "WE");
		obj5.put("ZSREG", "NORTH");
		obj5.put("MTPOS", "BANC");
		obj5.put("KUNNR", "1213058450");
		obj5.put("KUNAG", "1213058450");
		obj5.put("VTWEG", "11");
		obj5.put("AUTLF", "X");
		obj5.put("GRKOR", "");
		
		JSONObject obj6 = new JSONObject();
		obj6.put("ZGROU", "3");
		obj6.put("POSNR", "10");
		obj6.put("VKORG", "US31");
		obj6.put("AUART", "YBOR");
		obj6.put("LAND1", "US");
		obj6.put("VKBUR", "US21");
		obj6.put("MATNR", "U01T3C1");
		obj6.put("VSBED", "L4");
		obj6.put("MVGR1", "XHS");
		obj6.put("ZFAMIL", "");
		obj6.put("ZMACTY", "9532");
		obj6.put("ZZGEO", "MG");
		obj6.put("ZZSGEO", "NA");
		obj6.put("ZSREG", "");
		obj6.put("MTPOS", "YSBN");
		obj6.put("KUNNR", "1213415451");
		obj6.put("KUNAG", "1213415451");
		obj6.put("VTWEG", "21");
		obj6.put("AUTLF", "");
		obj6.put("GRKOR", "1");
		
		JSONObject obj7 = new JSONObject();
		obj7.put("ZGROU", "3");
		obj7.put("POSNR", "20");
		obj7.put("VKORG", "US31");
		obj7.put("AUART", "YBOR");
		obj7.put("LAND1", "US");
		obj7.put("VKBUR", "US21");
		obj7.put("MATNR", "00Y3450");
		obj7.put("VSBED", "L4");
		obj7.put("MVGR1", "");
		obj7.put("ZFAMIL", "");
		obj7.put("ZMACTY", "00Y3");
		obj7.put("ZZGEO", "MG");
		obj7.put("ZZSGEO", "NA");
		obj7.put("ZSREG", "");
		obj7.put("MTPOS", "YSSL");
		obj7.put("KUNNR", "1213415451");
		obj7.put("KUNAG", "1213415451");
		obj7.put("VTWEG", "21");
		obj7.put("AUTLF", "");
		obj7.put("GRKOR", "");
		
		JSONObject obj8 = new JSONObject();
		obj8.put("ZGROU", "3");
		obj8.put("POSNR", "30");
		obj8.put("VKORG", "US31");
		obj8.put("AUART", "YBOR");
		obj8.put("LAND1", "US");
		obj8.put("VKBUR", "US21");
		obj8.put("MATNR", "9532AC1");
		obj8.put("VSBED", "L4");
		obj8.put("MVGR1", "XHS");
		obj8.put("ZFAMIL", "");
		obj8.put("ZMACTY", "9532");
		obj8.put("ZZGEO", "MG");
		obj8.put("ZZSGEO", "NA");
		obj8.put("ZSREG", "");
		obj8.put("MTPOS", "YSCB");
		obj8.put("KUNNR", "1213415451");
		obj8.put("KUNAG", "1213415451");
		obj8.put("VTWEG", "21");
		obj8.put("AUTLF", "");
		obj8.put("GRKOR", "1");
		
		JSONObject obj9 = new JSONObject();
		obj9.put("ZGROU", "4");
		obj9.put("POSNR", "10");
		obj9.put("VKORG", "JP30");
		obj9.put("AUART", "YBOL");
		obj9.put("LAND1", "JP");
		obj9.put("VKBUR", "JP21");
		obj9.put("MATNR", "U003X81");
		obj9.put("VSBED", "OC");
		obj9.put("MVGR1", "XHS");
		obj9.put("ZFAMIL", "");
		obj9.put("ZMACTY", "5462");
		obj9.put("ZZGEO", "MG");
		obj9.put("ZZSGEO", "JAPAN");
		obj9.put("ZSREG", "");
		obj9.put("MTPOS", "YSBB");
		obj9.put("KUNNR", "1214855918");
		obj9.put("KUNAG", "1214855918");
		obj9.put("VTWEG", "11");
		obj9.put("AUTLF", "");
		obj9.put("GRKOR", "");
		
		JSONObject obj10 = new JSONObject();
		obj10.put("ZGROU", "4");
		obj10.put("POSNR", "20");
		obj10.put("VKORG", "JP30");
		obj10.put("AUART", "YBOL");
		obj10.put("LAND1", "JP");
		obj10.put("VKBUR", "JP21");
		obj10.put("MATNR", "1754A1X");
		obj10.put("VSBED", "OC");
		obj10.put("MVGR1", "XHO");
		obj10.put("ZFAMIL", "");
		obj10.put("ZMACTY", "1754");
		obj10.put("ZZGEO", "MG");
		obj10.put("ZZSGEO", "JAPAN");
		obj10.put("ZSREG", "");
		obj10.put("MTPOS", "YSBN");
		obj10.put("KUNNR", "1214855918");
		obj10.put("KUNAG", "1214855918");
		obj10.put("VTWEG", "11");
		obj10.put("AUTLF", "");
		obj10.put("GRKOR", "");
		
		
		JSONArray array = new JSONArray();
		array.add(obj1);
		array.add(obj2);
		array.add(obj3);
		array.add(obj4);
		array.add(obj5);
		array.add(obj6);
		array.add(obj7);
		array.add(obj8);
		array.add(obj9);
		array.add(obj10);
		
		JSONObject objall = new JSONObject();
		objall.put("data", array);
		
		return objall.toString();
	}
	
	public static void main(String args[]) {
		// send POST request
		String s1 = TestPOC.sendPocPost(packageData());
		System.out.println(s1);
		
		// send GET request
		// String s2 = TestPOC.sendPocGet("");
		// System.out.println(s2);
	}
}
