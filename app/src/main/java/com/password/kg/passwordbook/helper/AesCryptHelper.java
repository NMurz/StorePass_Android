package com.password.kg.passwordbook.helper;

import android.util.Log;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Nurs on 28.02.2016.
 */
public class AesCryptHelper {

    private static byte[] AESKEY = hexStringToByteArray("70F9F19BE57F30E5E5A0B518026F054A826857E0AEF5BE2D721E57991DA526C1");
    private static byte[] IV = hexStringToByteArray("64212DA7199EC25531DE6F5963DA419E");

    public static String aesEncrypt(String plaintext)
    {
        if(AESKEY == null || (AESKEY.length != 16 && AESKEY.length != 24 && AESKEY.length != 32))
        {

            Log.d("Aes","key's bit length is not 128/192/256");
            return null;
        }
        if(IV != null && IV.length != 16)
        {
            Log.d("Aes", "iv's bit length is not 16");
            return null;
        }

        try
        {
            SecretKeySpec keySpec = null;
            Cipher cipher = null;
            if(IV != null)
            {
                keySpec = new SecretKeySpec(AESKEY, "AES");
                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV));
            }
            else  //if(iv == null)
            {
                keySpec = new SecretKeySpec(AESKEY, "AES");
                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            }

            return new String(cipher.doFinal(plaintext.getBytes("UTF-8")));
        }
        catch(Exception e)
        {
            //  Logger.e(e.toString());
        }
        return null;
    }

    /**
     * AES decrypt function
     *
     //* @param encrypted
     //* @param AESKEY 16, 24, 32 bytes available
     //* @param iv initial vector (16 bytes) - if null: ECB mode, otherwise: CBC mode
     * @return
     */
    public static String aesDecrypt(String encryptedtext)
    {
        if(AESKEY == null || (AESKEY.length != 16 && AESKEY.length != 24 && AESKEY.length != 32))
        {
            //  Logger.e("key's bit length is not 128/192/256");
            return null;
        }
        if(IV != null && IV.length != 16)
        {
            //  Logger.e("iv's bit length is not 16");
            return null;
        }

        try
        {
            SecretKeySpec keySpec = null;
            Cipher cipher = null;
            if(IV != null)
            {
                keySpec = new SecretKeySpec(AESKEY, "AES");
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV));
            }
            else  //if(iv == null)
            {
                keySpec = new SecretKeySpec(AESKEY, "AES");
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            }

            return new String(cipher.doFinal(encryptedtext.getBytes("UTF-8")));
        }
        catch(Exception e)
        {
            Log.d("aes error", e.toString());
        }
        return null;
    }

    public static String byteArrayToHexString(byte[] bytes)
    {
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<bytes.length; i++)
        {
            if(((int)bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    /**
     * converts given hex string to a byte array
     * (ex: "0D0A" => {0x0D, 0x0A,})
     *
     * @param str
     * @return
     */
    public static final byte[] hexStringToByteArray(String str)
    {
        int i = 0;
        byte[] results = new byte[str.length() / 2];
        for (int k = 0; k < str.length();)
        {
            results[i] = (byte)(Character.digit(str.charAt(k++), 16) << 4);
            results[i] += (byte)(Character.digit(str.charAt(k++), 16));
            i++;
        }
        return results;
    }
}
