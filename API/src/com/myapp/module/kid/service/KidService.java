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
	 * 根据kidid查找孩子信息
	 * 
	 * @title: findKidById
	 * @author sangyue
	 * @date Jun 17, 2017 5:37:33 PM
	 * @param kidid
	 * @return
	 * @version V1.0
	 */
	public static Kid findKidById(String kidid) {
		String sql = "select nickname, sex, birthday, headurl, healthIssue from tb_kid where id = ? ";
		Kid kid = new Kid().findFirst(sql, kidid);
		return kid;
	}
}
