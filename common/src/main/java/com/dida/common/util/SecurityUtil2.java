package com.dida.common.util;

import android.os.Build;

import androidx.annotation.RequiresApi;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import android.util.Base64; // 수정된 import 문
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SecurityUtil2 {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String rsaEncode(String plainText, String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance(INSTANCE_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, convertPublicKey(publicKey));

        byte[] plainTextByte = cipher.doFinal(plainText.getBytes());

        return base64EncodeToString(plainTextByte);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Key convertPublicKey(String publicKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(INSTANCE_TYPE);
        byte[] publicKeyByte = Base64.decode(publicKey, Base64.URL_SAFE); // 수정된 부분

        return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByte));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String base64EncodeToString(byte[] byteData) {
        return Base64.encodeToString(byteData, Base64.URL_SAFE); // 수정된 부분
    }

    private static final String INSTANCE_TYPE = "RSA";
}
