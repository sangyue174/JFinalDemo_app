package com.myapp.service;

import com.jfinal.plugin.activerecord.Db;
import com.myapp.module.User;
import com.myapp.utils.TokenUtil;

/**
 * 登录的动作
 *
 */
public class LoginService {
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static User getUserInfo(String username, String pwd) {
		String sql = "SELECT * FROM tb_user WHERE username=?";// sql语句中？可以防止sql注入，多参数多？
		User user = new User().findFirst(sql, username);// 使用findFirst来实现指定查找，并且查找到的数据会以反射的形式来给User实体类
		return user;// 返回User实体类
	}

	/**
	 * 插入token验证
	 * 
	 * @return
	 */
	public static String insertTOKEN(String username) {
		String key = TokenUtil.generalKey();
		String sql = " UPDATE tb_user SET token=? WHERE username=?";
		Db.update(sql, key, username);//不关联任何实体类的方法，其中有增删改查方法，可以自己来实现下看看
		return key;
	}
}
