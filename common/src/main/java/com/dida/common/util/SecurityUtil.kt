import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

fun String.encryptWithPublicKey(publicKeyString: String): String {
    try {
        // Base64 디코딩된 공개 키 문자열을 byte 배열로 변환
        val publicKeyBytes = android.util.Base64.decode(publicKeyString, android.util.Base64.NO_WRAP)

        // 공개 키 생성
        val keySpec = X509EncodedKeySpec(publicKeyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey: PublicKey = keyFactory.generatePublic(keySpec)

        // RSA 암호화 초기화
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        // 메시지를 바이트 배열로 변환하여 암호화
        val encryptedBytes = cipher.doFinal(this.toByteArray(Charsets.UTF_8))

        // 암호화된 바이트 배열을 Base64 문자열로 변환하여 반환
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}
