import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Main {

    public int bitLength = 156;
    public int certainty = 100;
    Random rnd = new Random();

    private BigInteger p;
    private BigInteger q;

    public String msg = "Hello World!";

    public static void main(String[] args) {

        Main main = new Main();
        // Generate two prime number
        main.genPrimeNumbers();

        // Calculate n = p * q
        BigInteger n = main.p.multiply(main.q);

        // Calculate ф(n) = (p-1) * (q-1)
        BigInteger phi = main.p.subtract(BigInteger.ONE).multiply(main.q.subtract(BigInteger.ONE));

        // Find the relative e for phi
        BigInteger e = findRelativePrime(phi);

        // Calculate d, the inverse of e modulo ф(n)

        // Direct method
        BigInteger d = e.modInverse(phi);

        // Convert string message to byte
        BigInteger msgBigInt = new BigInteger(main.msg.getBytes(StandardCharsets.UTF_8));

        // Encrypt the message
        BigInteger cipherText = main.encrypt(msgBigInt, e, n);

        BigInteger decryptedText = main.decrypt(cipherText, d, n);

        // Convert message to string
        String decryptedStrMsg = new String(decryptedText.toByteArray(), StandardCharsets.UTF_8);



        // Output the results
        System.out.println("n = p * q = " + n);
        System.out.println("ф(n) = (p - 1) * (q - 1) = " + phi);
        System.out.println("e : " + e);
        System.out.println("d : " + d);

        // Test
        System.out.println("EncryptedText: " + cipherText);
        System.out.println("DecryptedText: " + decryptedStrMsg);
    }

    public void genPrimeNumbers() {
        p = BigInteger.probablePrime(bitLength, rnd);

        do {
            q = BigInteger.probablePrime(bitLength, rnd);
        }
        while (p.equals(q));

        // Output the generated primes
        System.out.println("Prime p: " + p);
        System.out.println("Prime q: " + q);
    }


    public static BigInteger findRelativePrime(BigInteger phi) {
        BigInteger e = new BigInteger("2");

        while (e.gcd(phi).compareTo(BigInteger.ONE) != 0) {
             e = e.add(BigInteger.ONE);
        }

        return e;
    }

    public BigInteger encrypt(BigInteger plainText, BigInteger e, BigInteger n) {
        return plainText.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger cipherText, BigInteger d, BigInteger n) {
        return cipherText.modPow(d, n);
    }



}
