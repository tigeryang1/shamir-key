# Shamir's Secret Sharing
App that creates an RSA key pair and shards the private key into k of n shares using Shamir's secret sharing algorithm. The app should be able to re-create the private key if 2 of n shares are presented. 

The program should write the public key to a text file called Public.TXT, and the private key shards to text files called Shard[k].TXT.

Demonstrate that the program:

Creates the RSA key pair with a Private Key broken into 5 shards.
Encrypts a random plain text string using the RSA Public Key.
Reassembles the Private Key using shard 2 & 5.
Decrypts the cypher text back into the plain text using the reassembled Private Key.
Asserts the decrypted plain text is equal to the original random plain text in Step 2.



# Steps to run

1. import as maven project in Eclipse 
2. Run maven install 
3. Run /test/java/com/blockchain/shamir/perf/ShamirExample.java as Java Application 
