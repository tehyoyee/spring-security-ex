package com.demo.securitytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

@SpringBootApplication
public class SecuritytestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritytestApplication.class, args);


//		String salt = KeyGenerators.string().generateKey();
//		String password = "secret";
//		String valueToEncrypt = "HELLO";
//
//		BytesEncryptor e = Encryptors.standard(password, salt);
//		byte[] encrypted = e.encrypt(valueToEncrypt.getBytes());
//		byte[] decrypted = e.decrypt(encrypted);

		String salt = KeyGenerators.string().generateKey();
		String valueToEncrypt = "HEllo";
		String password = "secret";
		TextEncryptor e = Encryptors.text(password, salt);
		String encrypted = e.encrypt(valueToEncrypt);
		String decrypted = e.decrypt(encrypted);

		System.out.println("salt : " + salt);
		System.out.println("password : " + password);
		System.out.println("valueToEncrypt : " + valueToEncrypt);
		System.out.println("encrypted : " + encrypted.toString());
		System.out.println("decrypted : " + decrypted.toString());
	}


}
