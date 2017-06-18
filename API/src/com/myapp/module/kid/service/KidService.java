package com.myapp.module.kid.service;

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
	 * 根据kidid查询孩子信息
	 * 
	 * @title: findKidById
	 * @author sangyue
	 * @date Jun 17, 2017 5:37:33 PM
	 * @param kidid
	 * @return
	 * @version V1.0
	 */
	public static Kid findKidById(int kidid) {
		String sql = "select nickname, sex, birthday, headurl, healthIssue from tb_kid where id = ? ";
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
		String sql = "select nickname, sex, birthday, headurl, healthIssue from tb_kid where id = ? and userid = ? ";
		Kid kid = new Kid().findFirst(sql, kidid, userid);
		return kid;
	}
}
