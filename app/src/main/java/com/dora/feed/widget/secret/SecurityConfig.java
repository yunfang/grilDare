/*
 * Copyright 2015 Rocko (http://rocko.xyz) <rocko.zxp@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dora.feed.widget.secret;

import com.dora.feed.widget.secret.security.RSACipherStrategy;
import com.famlink.frame.util.CacheUtils;

import java.net.URL;

public class SecurityConfig {
	public final static String KEY = "ROCKO_ZHENGXIAOPENG";
	
//	public final static String RSA_PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+WiouLXlITohBzGZSAR0vEZOz\n" +
//			"xg5PgvbX6/etLpP4hUAgIDtj02VFmrU5VuG9+YDTgam5ahvjsZUAs3Uxmbl4tdw8\n" +
//			"IdqoTlHfbqoiHEwiKVQ8RmQEDrEJHMRA9PLL4AFSt4WKnv63N9XnEw5SIHUaL1dV\n" +
//			"l8ywvRBr8WrymdD14wIDAQAB";
//
//	public final static String RSA_PRIVATE_KEY = "MIICXAIBAAKBgQC+WiouLXlITohBzGZSAR0vEZOzxg5PgvbX6/etLpP4hUAgIDtj\n" +
//			"02VFmrU5VuG9+YDTgam5ahvjsZUAs3Uxmbl4tdw8IdqoTlHfbqoiHEwiKVQ8RmQE\n" +
//			"DrEJHMRA9PLL4AFSt4WKnv63N9XnEw5SIHUaL1dVl8ywvRBr8WrymdD14wIDAQAB\n" +
//			"AoGAI5GJjDNnFEHIaDMfc3dN0rvAE7mQdv8LzPEEyNGJSMjtYJNlRZP8unLcJii7\n" +
//			"6dmzXtI9uq2/JF7MbuMZpPkKlNcWX8knE9UFJIu1J1hkXBDeVViYDstwDk0OT624\n" +
//			"J8B2ualRsSHBppgdhXEnXjR0vm2MX0k0UFbCLVG+4wrc+zECQQDpZG/Vc+rlwx8M\n" +
//			"3z3fx0IeFG7MSeBI90savdDOwvdHsaYST2sLNsPsd525hi5GzXlv9EZAMJ24pQgF\n" +
//			"200XZDOdAkEA0Mpv83ROsdRU6eOUOO35kFzQYl3iTyLF+GVqAKUzcqwyRI+rhoLU\n" +
//			"8g+Gy7JhkJf5AGJU7BV0/xlRBhsbVYVXfwJAQFKr97ogzP3/ur50AQ6bjEq5Vpgt\n" +
//			"ti5hhpc1yyY0nI+7Y2R77fVD/hHhaFYwvta2V0KNcfd0IIVrNqIAFyhIiQJAVUj7\n" +
//			"pcRyiK0k6kzttLtwX4mqDSQwVwbrOuWiARV6CHNSLTNKay1x8lZpRzdcJwYMzh1c\n" +
//			"dvrkyXb747Sa27oV3QJBAOLQpiwWSpkEYH9oj4Qv3CmGiZGbypYQz0hXtbaLmpYR\n" +
//			"br1KyfVS5IlJTlMashRuyhl5CW1wU8WiQnSVvXmFwPg=";

	public final static String RSA_PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+WiouLXlITohBzGZSAR0vEZOz\n" +
			"xg5PgvbX6/etLpP4hUAgIDtj02VFmrU5VuG9+YDTgam5ahvjsZUAs3Uxmbl4tdw8\n" +
			"IdqoTlHfbqoiHEwiKVQ8RmQEDrEJHMRA9PLL4AFSt4WKnv63N9XnEw5SIHUaL1dV\n" +
			"l8ywvRBr8WrymdD14wIDAQAB";


	public final static String RSA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL5aKi4teUhOiEHM\n" +
			"ZlIBHS8Rk7PGDk+C9tfr960uk/iFQCAgO2PTZUWatTlW4b35gNOBqblqG+OxlQCz\n" +
			"dTGZuXi13Dwh2qhOUd9uqiIcTCIpVDxGZAQOsQkcxED08svgAVK3hYqe/rc31ecT\n" +
			"DlIgdRovV1WXzLC9EGvxavKZ0PXjAgMBAAECgYAjkYmMM2cUQchoMx9zd03Su8AT\n" +
			"uZB2/wvM8QTI0YlIyO1gk2VFk/y6ctwmKLvp2bNe0j26rb8kXsxu4xmk+QqU1xZf\n" +
			"yScT1QUki7UnWGRcEN5VWJgOy3AOTQ5PrbgnwHa5qVGxIcGmmB2FcSdeNHS+bYxf\n" +
			"STRQVsItUb7jCtz7MQJBAOlkb9Vz6uXDHwzfPd/HQh4UbsxJ4Ej3Sxq90M7C90ex\n" +
			"phJPaws2w+x3nbmGLkbNeW/0RkAwnbilCAXbTRdkM50CQQDQym/zdE6x1FTp45Q4\n" +
			"7fmQXNBiXeJPIsX4ZWoApTNyrDJEj6uGgtTyD4bLsmGQl/kAYlTsFXT/GVEGGxtV\n" +
			"hVd/AkBAUqv3uiDM/f+6vnQBDpuMSrlWmC22LmGGlzXLJjScj7tjZHvt9UP+EeFo\n" +
			"VjC+1rZXQo1x93QghWs2ogAXKEiJAkBVSPulxHKIrSTqTO20u3BfiaoNJDBXBus6\n" +
			"5aIBFXoIc1ItM0prLXHyVmlHN1wnBgzOHVx2+uTJdvvjtJrbuhXdAkEA4tCmLBZK\n" +
			"mQRgf2iPhC/cKYaJkZvKlhDPSFe1toualhFuvUrJ9VLkiUlOUxqyFG7KGXkJbXBT\n" +
			"xaJCdJW9eYXA+A==";


//	static RSACipherStrategy rsaCipherStrategy;
//	public static RSACipherStrategy getInstance()
//	{
//		if (rsaCipherStrategy == null) {
//			rsaCipherStrategy = new RSACipherStrategy();
//		}
//		return rsaCipherStrategy;
//	}

	/**
	 * OpenSSL RSA加密
	 */
	public static String encrypt(String sourceContent) {
		RSACipherStrategy rsaCipherStrategy = new RSACipherStrategy();
		// rsa 公钥加密
		rsaCipherStrategy.initPublicKey(SecurityConfig.RSA_PUCLIC_KEY);
		String rsaEncrypt = rsaCipherStrategy.encrypt(sourceContent);
//		// aes
//		String aesEncrypt = aesCipherStrategy.encrypt(sourceContent);
//		//des
//		String desEncrypt = desCipherStrategy.encrypt(sourceContent);


//		mBinding.encryptRsa.setText(rsaEncrypt);
//		mBinding.encryptAes.setText(aesEncrypt);
//		mBinding.encryptDes.setText(desEncrypt);
		return rsaEncrypt;
	}

	/**
	 * OpenSSL RSA解密
	 */
	public static String decrypt(String sourceContent) {
		RSACipherStrategy rsaCipherStrategy = new RSACipherStrategy();
		// rsa 私钥解密
		rsaCipherStrategy.initPrivateKey(SecurityConfig.RSA_PRIVATE_KEY);
		String rsaDecrypt = rsaCipherStrategy.decrypt(sourceContent);
//		// aes
//		String aesDecrypt = aesCipherStrategy.decrypt(aesEncrypt);
//		// des
//		String desDecrypt = desCipherStrategy.decrypt(desEncrypt);


//		mBinding.decryptRsa.setText(rsaDecrypt);
//		mBinding.decryptAes.setText(aesDecrypt);
//		mBinding.decryptDes.setText(desDecrypt);
		return rsaDecrypt;
	}
}
