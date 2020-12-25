package com.acgist.snail.gui.event.adapter;

import com.acgist.snail.gui.GuiManager;
import com.acgist.snail.gui.event.GuiEvent;
import com.acgist.snail.pojo.message.ApplicationMessage;

/**
 * <p>GUI隐藏窗口事件</p>
 * 
 * @author acgist
 */
public class HideEventAdapter extends GuiEvent {

	protected HideEventAdapter() {
		super(Type.HIDE, "隐藏窗口事件");
	}

	@Override
	protected void executeNative(Object ... args) {
		this.executeExtend(args);
	}
	
	@Override
	protected void executeExtend(Object ... args) {
		final ApplicationMessage message = ApplicationMessage.message(ApplicationMessage.Type.HIDE);
		GuiManager.getInstance().sendExtendGuiMessage(message);
	}
	
}
