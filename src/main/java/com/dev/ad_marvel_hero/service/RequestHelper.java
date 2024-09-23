package com.dev.ad_marvel_hero.service;

import com.dev.ad_marvel_hero.constants.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class RequestHelper {

    @Value("${marvel.publicKey}")
    private String publicKey;
    @Value("${marvel.privateKey}")
    private String privateKey;
    @Value("${marvel.baseUri}")
    private String baseUri;
    @Value("${marvel.characters.url}")
    private String charactersApi;
    @Value("${marvel.comics.url}")
    private String comicsApi;
    private final MessageDigest messageDigest;
    private static final String encodingCharset = "UTF-8";
    private static final String algorithm = "MD5";

    public RequestHelper() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance(algorithm);
    }

    public String getHashString(String timestamp){
        // Logic : hash_str = md5(f"{ts}{private_key}{public_key}".encode("utf8")).hexdigest()
        try {
            String inputString = timestamp + privateKey + publicKey ;
            byte[] messageBytes = messageDigest.digest(inputString.getBytes(encodingCharset));
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : messageBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexStringBuilder.append('0');
                hexStringBuilder.append(hex);
            }
            return hexStringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String createCharactersRequestUrl(int offset, int limit){
        String apiUrl = baseUri + charactersApi;
        String timestamp = String.valueOf(Instant.now());
        String hashString = getHashString(timestamp);
        return UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam(AppConstants.API_KEY, publicKey)
                .queryParam(AppConstants.TS, timestamp)
                .queryParam(AppConstants.HASH, hashString)
                .queryParam(AppConstants.ORDER_BY, AppConstants.NAME)
                .queryParam(AppConstants.LIMIT, String.valueOf(limit))
                .queryParam(AppConstants.OFFSET, String.valueOf(offset))
                .build().toUriString();
    }

    public String createComicsRequestUrl(String name){
        String apiUrl = baseUri + comicsApi;
        String timestamp = String.valueOf(Instant.now());
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam(AppConstants.CHARACTERS, name)
                .queryParam(AppConstants.API_KEY, publicKey)
                .queryParam(AppConstants.TS, timestamp)
                .queryParam(AppConstants.HASH, getHashString(timestamp))
                .build().toUriString();
    }
}
