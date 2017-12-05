package com.myapp.module.temprecord.service;

import java.util.List;

import com.myapp.bean.TempRecord;

/**
 * 温度记录Service
 * 
 * @className: TempRecordService
 * @author sangyue
 * @date Jun 14, 2017 12:12:54 AM
 * @version V1.0
 */
public class TempRecordService {

	/**
	 * 新增tempRecord
	 * 
	 * @title: saveTempRecord
	 * @author sangyue
	 * @date Jun 14, 2017 12:13:59 AM
	 * @param tempRecord
	 * @return
	 * @version V1.0
	 */
	public static boolean saveTempRecord(TempRecord tempRecord) {
		return tempRecord.save();
	}

	/**
	 * 更新tempRecord
	 * 
	 * @title: updateTempRecord
	 * @author sangyue
	 * @date Jun 14, 2017 12:14:45 AM
	 * @param tempRecord
	 * @return
	 * @version V1.0
	 */
	public static boolean updateTempRecord(TempRecord tempRecord) {
		return tempRecord.update();
	}

	/**
	 * 根据时间和设备id查询TempRecord
	 * 
	 * @title: findTempRecordByDate
	 * @author sangyue
	 * @date Jun 14, 2017 12:22:45 AM
	 * @param equipid
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public static List<TempRecord> findTempRecordByDate(Integer equipid,
			String startDate, String endDate) {
		String sql = "select recordTime, temperature from tb_temp_record where equipid = ? and Date(recordTime) between ? and ? ";
		List<TempRecord> tempRecordList = new TempRecord().find(sql, equipid,
				startDate, endDate);
		return tempRecordList;
	}
	
	/**
	 * 根据时间和孩子查询TempRecord
	 * @title: findTempRecordByDateAndKid
	 * @author sangyue
	 * @date Dec 4, 2017 6:11:51 PM
	 * @param kidid
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @version V1.0
	 */
	public static List<TempRecord> findTempRecordByDateAndKid(Integer kidid,
			String startDate, String endDate) {
		String sql = "select recordTime, temperature from tb_temp_record where kidid = ? and Date(recordTime) between ? and ? ";
		List<TempRecord> tempRecordList = new TempRecord().find(sql, kidid,
				startDate, endDate);
		return tempRecordList;
	}

	/**
	 * 根据时间设备id查询最高最低TempRecord
	 * 
	 * @title: findMaxMinTempRecordByDate
	 * @author sangyue
	 * @date Jun 14, 2017 12:30:48 AM
	 * @param equipid
	 * @param startDate
	 * @param endDate
	 * @return
	 * @version V1.0
	 */
	public static TempRecord findMaxMinTempRecordByDate(Integer equipid,
			String startDate, String endDate) {
		String sql = "select max(temperature) maxTemp, min(temperature) minTemp from tb_temp_record where equipid = ? and Date(recordTime) between ? and ? ";
		TempRecord tempRecord = new TempRecord().findFirst(sql, equipid,
				startDate, endDate);
		return tempRecord;
	}
	
	/**
	 * 根据时间和孩子查询最高最低TempRecord
	 * @title: findMaxMinTempRecordByDateAndKid
	 * @author sangyue
	 * @date Dec 4, 2017 6:13:05 PM
	 * @param kidid
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @version V1.0
	 */
	public static TempRecord findMaxMinTempRecordByDateAndKid(Integer kidid,
			String startDate, String endDate) {
		String sql = "select max(temperature) maxTemp, min(temperature) minTemp from tb_temp_record where kidid = ? and Date(recordTime) between ? and ? ";
		TempRecord tempRecord = new TempRecord().findFirst(sql, kidid,
				startDate, endDate);
		return tempRecord;
	}
	
	/**
	 * 查询当前设备涉及的所有日期
	 * @title: findTempRecordDate
	 * @author sangyue
	 * @date Jun 15, 2017 12:36:39 AM
	 * @param equipid
	 * @return 
	 * @version V1.0
	 */
	public static List<TempRecord> findTempRecordDateGroupbyRecordTime(Integer equipid) {
		String sql = "select Date(recordTime) recordTime from tb_temp_record where equipid = ? group by Date(recordTime) order by Date(recordTime) ";
		List<TempRecord> tempRecordList = new TempRecord().find(sql, equipid);
		return tempRecordList;
	}
	
	/**
	 * 查询孩子涉及的所有日期
	 * @title: findTempRecordDateByKidGroupbyRecordTime
	 * @author sangyue
	 * @date Dec 4, 2017 6:22:06 PM
	 * @param equipid
	 * @return 
	 * @version V1.0
	 */
	public static List<TempRecord> findTempRecordDateByKidGroupbyRecordTime(Integer kidid) {
		String sql = "select Date(recordTime) recordTime from tb_temp_record where kidid = ? group by Date(recordTime) order by Date(recordTime) ";
		List<TempRecord> tempRecordList = new TempRecord().find(sql, kidid);
		return tempRecordList;
	}

}
