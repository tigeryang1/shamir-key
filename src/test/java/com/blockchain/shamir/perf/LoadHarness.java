
package com.blockchain.shamir.perf;

import java.security.SecureRandom;

import com.blockchain.shamir.Scheme;

public class LoadHarness {

  public static void main(String[] args) {
    final byte[] secret = new byte[10 * 1024];
    final Scheme scheme = new Scheme(new SecureRandom(), 200, 20);
    for (int i = 0; i < 100_000_000; i++) {
      scheme.join(scheme.split(secret));
    }
  }
}
