package com.acgist.snail.system.config;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>MSE加密配置</p>
 * 
 * @author acgist
 * @since 1.1.0
 */
public final class CryptConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptConfig.class);
	
	/**
	 * 加密算法
	 */
	public enum CryptAlgo {
		
		/** 明文 */
		PLAINTEXT(0x01),
		/** ARC4 */
		ARC4(	  0x02);
		
		/**
		 * provide
		 */
		private final int provide;
		
		private CryptAlgo(int provide) {
			this.provide = provide;
		}

		public int provide() {
			return this.provide;
		}
		
	}
	
	/**
	 * 加密策略
	 */
	public enum Strategy {
		
		/** 明文 */
		PLAINTEXT(			false, CryptAlgo.PLAINTEXT.provide),
		/** 兼容：偏爱明文 */
		PREFER_PLAINTEXT(	false, CryptAlgo.PLAINTEXT.provide | CryptAlgo.ARC4.provide),
		/** 兼容：偏爱加密 */
		PREFER_ENCRYPT(		true,  CryptAlgo.ARC4.provide | CryptAlgo.PLAINTEXT.provide),
		/** 加密 */
		ENCRYPT(			true,  CryptAlgo.ARC4.provide);
		
		/**
		 * 是否加密
		 */
		private final boolean crypt;
		/**
		 * 加密模式：crypto_provide
		 */
		private final int provide;
		
		private Strategy(boolean crypt, int provide) {
			this.crypt = crypt;
			this.provide = provide;
		}
		
		public boolean crypt() {
			return this.crypt;
		}
		
		public int provide() {
			return this.provide;
		}
		
	}
	
	/**
	 * Prime P(768 bit safe prime)
	 */
	public static final BigInteger P = new BigInteger(
		"FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A36210000000000090563",
		16
	);
	/**
	 * Generator G
	 */
	public static final BigInteger G = BigInteger.valueOf(2);
	/**
	 * 公钥长度
	 */
	public static final int PUBLIC_KEY_LENGTH = 96;
	/**
	 * <p>私钥长度</p>
	 * <p>随机长度：128~180，超过180只能增加计算时间，并不能提高安全性。推荐长度：160</p>
	 */
	public static final int PRIVATE_KEY_LENGTH = 20;
	/**
	 * 填充最大随机长度
	 */
	public static final int PADDING_MAX_LENGTH = 512;
	/**
	 * VC长度
	 */
	public static final int VC_LENGTH = 8;
	/**
	 * VC数据：八字节（0x00）
	 */
	public static final byte[] VC = new byte[VC_LENGTH];
	/**
	 * 默认加密策略
	 */
	public static final Strategy STRATEGY = Strategy.PREFER_PLAINTEXT;

	static {
		LOGGER.info("默认加密策略：{}", CryptConfig.STRATEGY);
	}
	
}
