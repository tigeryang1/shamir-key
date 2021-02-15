package com.blockchain.shamir.perf;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Map;

import com.blockchain.shamir.Scheme;

public class SchemeSample {
	
	  public static void main(String[] args) {
		  final Scheme scheme = new Scheme(new SecureRandom(), 5, 3);
		    final byte[] secret = "[B@6d7b4f4c".getBytes(StandardCharsets.UTF_8);
		    final Map<Integer, byte[]> parts = scheme.split(secret);
		    final byte[] recovered = scheme.join(parts);
		    System.out.println(new String(recovered, StandardCharsets.UTF_8));

		  }

}
