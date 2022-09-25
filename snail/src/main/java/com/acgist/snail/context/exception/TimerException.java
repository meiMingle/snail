package com.acgist.snail.context.exception;

/**
 * 定时任务时间周期异常
 * 
 * @author acgist
 */
public final class TimerException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * 验证定时任务时间周期
	 * 
	 * @param duration 时间周期
	 */
	public static final void verify(long duration) {
		if(duration < 0) {
			throw new TimerException(duration);
		}
	}
	
	public TimerException() {
		super("定时任务时间周期异常");
	}
	
	/**
	 * @param duration 时间周期
	 */
	public TimerException(long duration) {
		super("定时任务时间周期错误：" + duration);
	}

	/**
	 * @param message 错误信息
	 */
	public TimerException(String message) {
		super(message);
	}

	/**
	 * @param cause 原始异常
	 */
	public TimerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message 错误信息
	 * @param cause 原始异常
	 */
	public TimerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
