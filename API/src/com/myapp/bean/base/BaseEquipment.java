package com.myapp.bean.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseEquipment<M extends BaseEquipment<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setEquipnum(java.lang.String equipnum) {
		set("equipnum", equipnum);
		return (M)this;
	}

	public java.lang.String getEquipnum() {
		return get("equipnum");
	}

	public M setIsactive(java.lang.String isactive) {
		set("isactive", isactive);
		return (M)this;
	}

	public java.lang.String getIsactive() {
		return get("isactive");
	}

}
