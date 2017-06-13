
package com.myapp.module.equipment.service;

import com.jfinal.plugin.activerecord.Db;
import com.myapp.bean.Equipment;

/**
 * 类说明: 
 * @ClassName: EquipmentService
 * @author: pengxiuxiao
 * @date: 2017年6月7日 下午10:31:45
*/
public class EquipmentService {

	/**
	 * 保存设备
	 * @param equipment
	 * @return 
	 */
	public static boolean saveEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		return equipment.save();
	}

	/**
	 * 删除设备
	 * @param number
	 * @return 
	 */
	public static int deleteEquipment(String number) {
		// TODO Auto-generated method stub
		String sql = "delete from tb_equipment where number = ?";
		return Db.update(sql, number);
	}

	/**
	 * 根据id查找设备
	 * @param equipmentId
	 * @return
	 */
	public static Equipment findEquipment(Integer equipmentId) {
		// TODO Auto-generated method stub
		return Equipment.dao.findById(equipmentId);
	}

}
