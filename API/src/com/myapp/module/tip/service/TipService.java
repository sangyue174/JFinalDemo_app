package com.myapp.module.tip.service;

import java.util.List;

import com.myapp.bean.Tip;

/**
 * 贴士service
 * 
 * @className: TipService
 * @author sangyue
 * @date Jun 17, 2017 11:28:31 AM
 * @version V1.0
 */
public class TipService {
	/**
	 * 根据tipType查找贴士列表
	 * 
	 * @title: findTipByTipType
	 * @author sangyue
	 * @date Jun 17, 2017 11:31:09 AM
	 * @param tipType
	 * @return
	 * @version V1.0
	 */
	public static List<Tip> findTipByTipType(String tipType) {
		String sql = "select id, number, imageUrl title from tb_tip where tipType = ? order by number ";
		List<Tip> tipList = new Tip().find(sql, tipType);
		return tipList;
	}
	
	/**
	 * 根据tipid查找贴士内容
	 * 
	 * @title: findTipContentById
	 * @author sangyue
	 * @date Jun 17, 2017 11:37:57 AM
	 * @param tipid
	 * @return
	 * @version V1.0
	 */
	public static Tip findTipContentById(String tipid) {
		String sql = "select number, imageUrl, title, content from tb_tip where id = ? ";
		Tip tip = new Tip().findFirst(sql, tipid);
		return tip;
	}
}
