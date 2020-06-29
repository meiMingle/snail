package com.acgist.snail.pojo.entity;

/**
 * <p>Entity - 配置</p>
 * 
 * @author acgist
 * @since 1.0.0
 */
public final class ConfigEntity extends Entity {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>配置表名：{@value}</p>
	 */
	public static final String TABLE_NAME = "tb_config";
	/**
	 * <p>配置名称：{@value}</p>
	 */
	public static final String PROPERTY_NAME = "name";
	/**
	 * <p>配置值：{@value}</p>
	 */
	public static final String PROPERTY_VALUE = "value";

	/**
	 * <p>配置名称</p>
	 */
	private String name;
	/**
	 * <p>配置值</p>
	 */
	private String value;

	/**
	 * <p>获取配置名称</p>
	 * 
	 * @return 配置名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>设置配置名称</p>
	 * 
	 * @param name 配置名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>获取配置值</p>
	 * 
	 * @return 配置值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>设置配置值</p>
	 * 
	 * @param value 配置值
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
