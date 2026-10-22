/*
 * ServerApplication.java
 * CS 305: Software Security
 * Module Five Coding Assignment: Checksum Verification
 * Author: Cameron Price
 *
 * Refactored from the provided skeleton to compute and return a SHA-256 checksum
 * over a unique data string.
 */
package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

@RestController
class ServerController {

    /** The algorithm is named in one place so the response and the digest cannot disagree. */
    private static final String ALGORITHM = "SHA-256";

    @RequestMapping("/hash")
    public String myHash() throws NoSuchAlgorithmException {
        String data = "Hello Cameron Price!";
        String checksum = computeChecksum(data);

        return "<p>data: " + data + "</p>"
             + "<p>Name of Cipher Algorithm Used: " + ALGORITHM + "</p>"
             + "<p>Checksum Value: " + checksum + "</p>";
    }

    /**
     * Returns the hexadecimal SHA-256 digest of the supplied string.
     *
     * The charset is stated explicitly rather than left to the platform default, because the
     * same text encoded as UTF-8 and as UTF-16 produces two different digests, and a checksum
     * that changes with the machine it ran on is not a checksum.
     */
    private String computeChecksum(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder(hashBytes.length * 2);
        for (byte b : hashBytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
