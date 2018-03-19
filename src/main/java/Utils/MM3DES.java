package Utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class MM3DES {
	
	private static final byte[] key=Base64.decodeBase64("e445f9a0f23f436f8629681464ec219b");
	/*private static final byte[] key= {1,2,3,4,5,6,7,8,9,0};*/
	private static final byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 }; 
	
    public static String decode(String c) throws Exception {
        byte[] data=Base64.decodeBase64(c);
        byte[] r = des3DecodeCBC(key, keyiv, data); 
		return  new String(r,"UTF-8");  
    }
    
    public static String encode(String c) throws Exception {
        byte[] data=c.getBytes("UTF-8");  
        byte[] r = des3EncodeCBC(key, keyiv, data);  ;  
		return  new String(Base64.encodeBase64(r));  
    }
    
    
	/** 
     * ECB加密,不要IV 
     * @param key 密钥 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    private static byte[] des3EncodeECB(byte[] key, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");  
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * ECB解密,不要IV 
     * @param key 密钥 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    private static byte[] des3DecodeECB(byte[] key, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * CBC加密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    /** 
     * CBC解密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    private static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
}
