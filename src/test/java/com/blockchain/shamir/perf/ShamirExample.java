package com.blockchain.shamir.perf;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;

import com.blockchain.utility.RSAEncryption;
import com.blockchain.utility.ShardFile;

public class ShamirExample {
	public static void main(String args[]) {

		try {

		
			Path currentRelativePath = Paths.get("/secrete");
			Files.createDirectories(currentRelativePath);												
			String path = currentRelativePath.toAbsolutePath().toString();
			System.out.println("Current relative path is: " + path);
						
			String plainText = ShardFile.generateRandomString(15);

			System.out.println("random string is : "+plainText);

			
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

			keyGen.initialize(2048);
			KeyPair generatedKeyPair = keyGen.genKeyPair();
			
			System.out.println("Creates the RSA key pair with a Private Key broken into 5 shards.\r\n ");
		
			PrivateKey priv = generatedKeyPair.getPrivate();

			ShardFile.SavePublicKey(path, generatedKeyPair.getPublic());
			System.out.println("Write the public key to a text file called Public.TXT, and the private key shards to text files called Shard[k].TXT \r\n");
			ShardFile.shardPrivateKey(priv, path, 5, 2);
			
			System.out.println("\r\n");
			
			System.out.println("Encrypts a random plain text string using the RSA Public Key.\r\n");
			
			String encryptedText = RSAEncryption.encryptMessageWithPub(plainText, generatedKeyPair.getPublic());

			int[] intArray = { 2, 5 };
			
			System.out.println("Reassembles the Private Key using shard 2 & 5.\r\n");
			
			PrivateKey privateKey = ShardFile.recoverPrivateKey(path, 5, 2, intArray);
			
			System.out.println("Decrypts the cypher text back into the plain text using the reassembled Private Key.\r\n");
			
			String descryptedText = RSAEncryption.decryptMessageWithPri(encryptedText, privateKey);

	
	
			System.out.println("decrypted: " + descryptedText);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
