package com.acgist.snail.net.torrent.dht.bootstrap.request;

import com.acgist.snail.config.DhtConfig;
import com.acgist.snail.net.torrent.dht.bootstrap.DhtRequest;
import com.acgist.snail.net.torrent.dht.bootstrap.response.PingResponse;

/**
 * <p>Ping</p>
 * 
 * @author acgist
 */
public final class PingRequest extends DhtRequest {

	private PingRequest() {
		super(DhtConfig.QType.PING);
	}
	
	/**
	 * <p>创建请求</p>
	 * 
	 * @return 请求
	 */
	public static final PingRequest newRequest() {
		return new PingRequest();
	}

	/**
	 * <p>处理请求</p>
	 * 
	 * @param request 请求
	 * 
	 * @return 响应
	 */
	public static final PingResponse execute(DhtRequest request) {
		return PingResponse.newInstance(request);
	}
	
}
