package com.myapp.module.user.service;

import com.jfinal.plugin.activerecord.Db;
import com.myapp.bean.User;
import com.myapp.bean.UserAuth;
import com.myapp.utils.PasswordUtil;

/**
 * 用户service
 * 
 * @className: UserService
 * @author sangyue
 * @date May 21, 2017 3:41:08 PM
 * @version V1.0
 */
public class UserService {
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static User getUserInfo(String username, String pwd) {
		String sql = "SELECT * FROM tb_user WHERE username=? and isactive = 1";// sql语句中？可以防止sql注入，多参数多？
		User user = new User().findFirst(sql, username);// 使用findFirst来实现指定查找，并且查找到的数据会以反射的形式来给User实体类
		return user;// 返回User实体类
	}

	/**
	 * 插入token验证
	 * 
	 * @return
	 */
	public static String insertTOKEN(String username) {
		String key = PasswordUtil.generalTokenKey();
		String sql = " UPDATE tb_user SET token=? WHERE username=?";
		Db.update(sql, key, username);// 不关联任何实体类的方法，其中有增删改查方法，可以自己来实现下看看
		return key;
	}

	/**
	 * 保存user
	 * 
	 * @title: saveUser
	 * @author sangyue
	 * @date May 21, 2017 11:38:41 AM
	 * @param user
	 * @return
	 * @version V1.0
	 */
	public static boolean saveUser(User user) {
		return user.save();
	}

	/**
	 * 新增userAuth
	 * 
	 * @title: saveUserAuth
	 * @author sangyue
	 * @date May 21, 2017 11:38:53 AM
	 * @param userAuth
	 * @return
	 * @version V1.0
	 */
	public static boolean saveUserAuth(UserAuth userAuth) {
		return userAuth.save();
	}
	
	/**
	 * 更新userAuth
	 * @title: updateUserAuth
	 * @author sangyue
	 * @date Jun 5, 2017 11:40:01 PM
	 * @param userAuth
	 * @return 
	 * @version V1.0
	 */
	public static boolean updateUserAuth(UserAuth userAuth) {
		return userAuth.update();
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @title: checkUserAuth
	 * @author sangyue
	 * @date May 21, 2017 7:45:20 PM
	 * @param identityType
	 * @param identifier
	 * @return
	 * @version V1.0
	 */
	public static UserAuth checkUserAuth(String identityType, String identifier) {
		String sql = "select * from tb_user_auth where identityType = ? and identifier = ? and verified = 1";
		UserAuth userAuth = new UserAuth().findFirst(sql, identityType,
				identifier);
		return userAuth;
	}
	
	/**
	 * 根据tokenKey查找用户
	 * @title: findUserAuthByTokenKey
	 * @author sangyue
	 * @date Jun 11, 2017 10:37:48 PM
	 * @param tokenKey
	 * @return 
	 * @version V1.0
	 */
	public static UserAuth findUserAuthByTokenKey(String tokenKey) {
		String sql = "select * from tb_user_auth where tokenKey = ? and verified = 1";
		UserAuth userAuth = new UserAuth().findFirst(sql, tokenKey);
		return userAuth;
	}
}
