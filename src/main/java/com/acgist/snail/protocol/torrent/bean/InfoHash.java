package com.acgist.snail.protocol.torrent.bean;

import java.util.Objects;

import com.acgist.snail.system.exception.DownloadException;
import com.acgist.snail.utils.Base32Utils;
import com.acgist.snail.utils.StringUtils;

/**
 * 种子InfoHash
 */
public class InfoHash {

	private final byte[] data;
	
	private InfoHash(byte[] data) {
		this.data = data;
	}

	public static final InfoHash newInstance(byte[] data) {
		return new InfoHash(data);
	}
	
	public static final InfoHash newInstance(String hash) throws DownloadException {
		hash = Objects.requireNonNull(hash, "不支持的hash");
		if(hash.length() == 40) {
			return newInstance(StringUtils.unhex(hash));
		} else if(hash.length() == 32) {
			byte[] data = Base32Utils.decode(hash);
			return newInstance(data);
		} else {
			throw new DownloadException("不支持的hash：" + hash);
		}
	}
	
	/**
	 * 磁力链接hash（40位）
	 */
	public String hash() {
		return StringUtils.sha1(data);
	}
	
	/**
	 * 种子ID（网络传输使用）
	 */
	public String hashId() {
		final String magnetHash = hash();
		int index = 0;
		final int length = magnetHash.length();
		final StringBuilder builder = new StringBuilder();
		do {
			builder.append("%").append(magnetHash.substring(index, index + 2));
			index += 2;
		} while (index < length);
		return builder.toString();
	}
	
}
