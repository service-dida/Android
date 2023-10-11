import android.os.Build
import java.security.GeneralSecurityException
import java.security.spec.X509EncodedKeySpec
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.util.Base64
import javax.crypto.Cipher

private const val INSTANCE_TYPE = "RSA"

fun String.rsaEncode(publicKey: String): String? {
    try {
        val cipher = Cipher.getInstance(INSTANCE_TYPE)
        cipher.init(Cipher.ENCRYPT_MODE, convertPublicKey(publicKey))

        val plainTextByte = cipher.doFinal(this.toByteArray())

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(plainTextByte)
        } else {
            android.util.Base64.encodeToString(plainTextByte, android.util.Base64.DEFAULT)
        }
    }catch (e : GeneralSecurityException){
        e.printStackTrace()
    }
    // Unable to encrypt Token
    return null
}

@Throws(Exception::class)
private fun convertPublicKey(publicKey: String): PublicKey? {
    try {
        val keyBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(publicKey.toByteArray())
        } else {
            android.util.Base64.decode(publicKey, android.util.Base64.DEFAULT)
        }
        val keyFactory = KeyFactory.getInstance(INSTANCE_TYPE)
        val keySpec = X509EncodedKeySpec(keyBytes)
        return keyFactory.generatePublic(keySpec)
    }catch (e : GeneralSecurityException){
        e.printStackTrace()
    }
    return null
}

/*fun base64EncodeToString(byteData: ByteArray): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(byteData)
    } else {
        android.util.Base64.encodeToString(byteData, android.util.Base64.DEFAULT)
    }
}*/
