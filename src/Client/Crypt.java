package Client;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Crypt
{
    private  static final String ALGORITHM = "RSA";

    private static final String TEXT = "Hallo dies ist ein Test!!!";

    PublicKey pubKey;

    public PublicKey getPubKey() {
        return pubKey;
    }

    public void setPubKey(PublicKey pubKey) {
        this.pubKey = pubKey;
    }

    public PrivateKey getPrivKey() {
        return privKey;
    }

    public void setPrivKey(PrivateKey privKey) {
        this.privKey = privKey;
    }

    PrivateKey privKey;

    public byte[] encrypt (PublicKey key, byte[] plain) throws  Exception
    {
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        //Verschlüsseln

        return  cipher.doFinal(plain);
    }

    public byte[] decrypt(PrivateKey key, byte[] chiffr) throws Exception
    {
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        //Entschlüsseln

        return cipher.doFinal(chiffr);
    }

    public void Schluesselpaar()
    {
        try
        {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);

            keyPairGen.initialize(1024);

            KeyPair keyPair = keyPairGen.generateKeyPair();

            PrivateKey privKey = keyPair.getPrivate();

            PublicKey pubKey = keyPair.getPublic();

            //Es werden die Bytes vom Text benötigt

            /*byte[] bytes = TEXT.getBytes(); // TEXT ist austauschen gegen unseren String

            byte[] encrypt = encrypt(pubKey, bytes);

            byte[] decrypt = decrypt(privKey, encrypt);*/

            /*System.out.println(encrypt);

            System.out.println("\n");

            System.out.println(decrypt);
            System.out.println("Verschlüsselt: " + new String(decrypt));

            String ausgabe = new String(decrypt);
            System.out.println(ausgabe);*/

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}


