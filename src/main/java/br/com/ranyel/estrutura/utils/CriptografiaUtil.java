package br.com.ranyel.estrutura.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class CriptografiaUtil {
	
	public String encripta(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] mensagem = msg.getBytes("UTF-8");
		byte[] chave = "autorizado10+rpl".getBytes("UTF-8");
		
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"));
		byte[] encrypted = cipher.doFinal(mensagem);
		
		String retorno = DatatypeConverter.printBase64Binary(encrypted);
		System.out.println(retorno);
		return retorno;
		
	}

	public String decripta(String msg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] mensagem = DatatypeConverter.parseBase64Binary(msg);
		byte[] chave = "autorizado10+rpl".getBytes("UTF-8");
		
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chave, "AES"));
		byte[] decrypted = cipher.doFinal(mensagem);
		
		String retorno = new String(decrypted);
		System.out.println(retorno);
		return retorno;
	}
}