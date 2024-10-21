package com.hospital.controller;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class AES {

	public static String encrypt(String data) {

		try {

			String key = "ThisIsMySecretKe";
			SecretKeySpec secretKeySpec = AesUtils.generateKey(key);
			System.out.println(secretKeySpec.toString());

			String encryptedData = AesUtils.encrypt(data, secretKeySpec);
			System.out.println("Encrypted data: " + encryptedData);

			String decryptedData = AesUtils.decrypt(encryptedData, secretKeySpec);
			System.out.println("Decrypted data: " + decryptedData);

			return encryptedData;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

}
