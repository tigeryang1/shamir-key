package com.blockchain.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.blockchain.shamir.Scheme;

public class ShardFile {

	public static void shardPrivateKey(PrivateKey priv,String path,int total, int limit) throws IOException {
		byte[] privateKeyBytes = priv.getEncoded();
		String privKeyStr = new String(Base64.getEncoder().encode(privateKeyBytes));
		  final Scheme scheme = new Scheme(new SecureRandom(), 5, 2);
		    final byte[] secret = privKeyStr.getBytes(StandardCharsets.UTF_8);
		    final Map<Integer, byte[]> parts = scheme.split(secret);
			int count=1;
			  for (Map.Entry<Integer, byte[]> entry : parts.entrySet())
			  {
					
					 System.out.println("Key = " + entry.getKey() + ", Value = " + new
					 String(Base64.getEncoder().encode(entry.getValue())));
					 
              
			    SavePrivateKey( path, entry.getValue(),count) ;
			    count++;
			  }
			  
	}
	
	
	public static PrivateKey recoverPrivateKey(String path,int total, int limit,int[] intArray) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		final Scheme scheme = new Scheme(new SecureRandom(), 5, 2);	  
		  
	    final Map<Integer, byte[]> parts_2 = new HashMap<Integer, byte[]> ();
	    for (int i = 0; i < intArray.length; i++)
	    {
        	parts_2.put(intArray[i],LoadPrivatePair( path,intArray[i]));
      
	    }	
	    final byte[] recovered = scheme.join(parts_2);
	    
	    String recoveredString= new String(recovered, StandardCharsets.UTF_8);
	    
	    byte[] sigBytes = new byte[0];
	    PrivateKey privateKey = null;

	
	        sigBytes = Base64.getDecoder().decode(recoveredString.getBytes("UTF-8"));
	  
	    
	    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(sigBytes);
	    KeyFactory keyFact = KeyFactory.getInstance("RSA");
	    
	  
	        privateKey = keyFact.generatePrivate(privateKeySpec);  //throws exception
	  
	    return privateKey;
	}
	
	

	
	public static void SavePublicKey(String path, PublicKey publicKey) throws IOException {

		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(path + "/Public.TXT");
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();	
	}
	
	public static void SavePrivateKey(String path, byte[] pri_sub,int n) throws IOException {

		FileOutputStream fos = new FileOutputStream(path + "/Shard"+n+".TXT");
		fos.write(pri_sub);
		fos.close();	
	}
	
	
   
	public static String generateRandomString(int n) throws IOException {

		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = n;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();	    
	    return generatedString;
	    
	}
	
	
	public static byte[] LoadPrivatePair(String path, int count)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
	
		// Read Private Key.
		File filePrivateKey = new File(path + "/Shard"+count+".TXT");
		FileInputStream fis = new FileInputStream(path + "/Shard"+count+".TXT");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
  
		return encodedPrivateKey;
	}
	
}
