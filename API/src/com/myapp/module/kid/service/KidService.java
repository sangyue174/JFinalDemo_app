package com.myapp.module.kid.service;

import java.util.List;

import com.myapp.bean.Kid;

/**
 * 孩子service
 * 
 * @className: KidService
 * @author sangyue
 * @date Jun 17, 2017 4:32:50 PM
 * @version V1.0
 */
public class KidService {
	/**
	 * 保存孩子信息
	 * 
	 * @title: saveKid
	 * @author sangyue
	 * @date Jun 17, 2017 5:00:41 PM
	 * @param kid
	 * @return
	 * @version V1.0
	 */
	public static boolean saveKid(Kid kid) {
		return kid.save();
	}
	
	/**
	 * 修改孩子信息
	 * 
	 * @title: updateKid
	 * @author sangyue
	 * @date Jun 18, 2017 4:49:15 PM
	 * @param kid
	 * @return
	 * @version V1.0
	 */
	public static boolean updateKid(Kid kid) {
		return kid.update();
	}

	/**
	 * 根据kidid查询孩子信息（增加温度报警和设备信息相关）
	 * 
	 * @title: findKidById
	 * @author sangyue
	 * @date Jun 17, 2017 5:37:33 PM
	 * @param kidid
	 * @return
	 * @version V1.0
	 */
	public static Kid findKidById(int kidid) {
		String sql = "select equipnum, nickname, sex, birthday, headurl, healthIssue, number, maxtime, mintime, isminalarm, ismaxalarm, isnotice from tb_kid where id = ? ";
		Kid kid = new Kid().findFirst(sql, kidid);
		return kid;
	}
	
	/**
	 * 根据用户和孩子id查询孩子信息
	 * 
	 * @title: findKidByUserAndId
	 * @author sangyue
	 * @date Jun 18, 2017 4:39:54 PM
	 * @param kidid
	 * @return
	 * @version V1.0
	 */
	public static Kid findKidByUserAndId(int kidid, int userid) {
		String sql = "select id, nickname, sex, birthday, headurl, healthIssue, number, equipid, equipnum from tb_kid where id = ? and userid = ? ";
		Kid kid = new Kid().findFirst(sql, kidid, userid);
		return kid;
	}
	
	/**
	 * 根据用户和设备编号查询孩子信息
	 * @title: findKidByEquipnumAndUser
	 * @author sangyue
	 * @date Dec 4, 2017 2:48:47 PM
	 * @param kidid
	 * @param userid
	 * @return 
	 * @version V1.0
	 */
	public static Kid findKidByEquipnumAndUser(String equipnum, int userid) {
		String sql = "select nickname, sex, birthday, headurl, healthIssue, number from tb_kid where equipnum = ? and userid = ? ";
		Kid kid = new Kid().findFirst(sql, equipnum, userid);
		return kid;
	}
	
	/**
	 * 根据设备编号查询孩子信息
	 * @title: findKidByEquipnum
	 * @author sangyue
	 * @date Dec 4, 2017 3:06:26 PM
	 * @param equipnum
	 * @return 
	 * @version V1.0
	 */
	public static Kid findKidByEquipnum(String equipnum) {
		String sql = "select userid, id, nickname, sex, birthday, headurl, healthIssue, number from tb_kid where equipnum = ?";
		Kid kid = new Kid().findFirst(sql, equipnum);
		return kid;
	}
	
	/**
	 * 根据userid查找孩子列表
	 * 
	 * @title: findKidListByUserid
	 * @author sangyue
	 * @date Jun 20, 2017 10:17:39 PM
	 * @param userid
	 * @return
	 * @version V1.0
	 */
	public static List<Kid> findKidListByUserid(int userid) {
		String sql = "select id, equipnum, nickname, sex, birthday, headurl, healthIssue, number, maxtime, mintime, isminalarm, ismaxalarm, isnotice from tb_kid where userid = ? order by number";
		List<Kid> kidList = new Kid().find(sql, userid);
		return kidList;
	}
}
