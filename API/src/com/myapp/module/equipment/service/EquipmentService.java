
package com.myapp.module.equipment.service;

import com.jfinal.plugin.activerecord.Db;
import com.myapp.bean.Equipment;
/**
 * 设备service
 * @className: EquipmentService
 * @author sangyue
 * @date Jun 18, 2017 5:37:53 PM
 * @version V1.0
 */
public class EquipmentService {

	/**
	 * 保存设备
	 * @param equipment
	 * @return 
	 */
	public static boolean saveEquipment(Equipment equipment) {
		return equipment.save();
	}

	/**
	 * 取消该设备
	 * @param number
	 * @return 
	 */
	public static int positiveEquipment(String number) {
		String sql = "update tb_equipment set isactive = '0' where number = ? ";
		return Db.update(sql, number);
	}
	
	/**
	 * 激活该设备
	 * @title: activeEquipment
	 * @author sangyue
	 * @date Jun 18, 2017 6:38:19 PM
	 * @param number
	 * @return 
	 * @version V1.0
	 */
	public static int activeEquipment(String number) {
		String sql = "update tb_equipment set isactive = '1' where number = ? ";
		return Db.update(sql, number);
	}
	
	/**
	 * 修改设备信息
	 * @title: updateEquipment
	 * @author sangyue
	 * @date Jun 18, 2017 6:20:27 PM
	 * @param equipment
	 * @return 
	 * @version V1.0
	 */
	public static boolean updateEquipment(Equipment equipment) {
		return equipment.update();
	}

	/**
	 * 根据设备编号查找设备
	 * @param equipmentId
	 * @return
	 */
	public static Equipment findEquipmentByNumber(String number) {
		String sql = "select id, kidid, number, maxtime, mintime, isminalarm, ismaxalarm, isnotice, isactive from tb_equipment where number = ? ";
		return new Equipment().findFirst(sql, number);
	}

}
