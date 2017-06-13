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
	 * 根据时间查询TempRecord
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
		String sql = "select * from tb_temp_record where equipid = ? and Date(recordTime) between ? and ? ";
		List<TempRecord> tempRecordList = new TempRecord().find(sql, equipid,
				startDate, endDate);
		return tempRecordList;
	}

	/**
	 * 根据时间查询最高最低TempRecord
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
		String sql = "select max(temperature), min(temperature) from tb_temp_record where equipid = ? and Date(recordTime) between ? and ? ";
		TempRecord tempRecord = new TempRecord().findFirst(sql, equipid,
				startDate, endDate);
		return tempRecord;
	}

}
