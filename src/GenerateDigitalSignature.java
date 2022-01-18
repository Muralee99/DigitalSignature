import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

public class GenerateDigitalSignature {

	public static void main(String[] args) {
		
		try {
		//Initialize KeyPairGenerator object
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(1024, random);
		
		// Get a Privatekey from the generated keypair
		KeyPair keyPair = keyGen.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		
		//Get an instance of Signature object and initialize it
		Signature signature = Signature.getInstance("SHA1withDSA", "SUN");
		signature.initSign(privateKey);
		
		//supply the data to be signed to the signature object
		//using the update() method and generate the digital signature
		byte[] bytes = Files.readAllBytes(Paths.get("D:\\Test_Files\\Test_document.docx"));
		signature.update(bytes);
		byte[] digitalSignature = signature.sign();
		
		//Save digital signature and the public key to a file
		Files.write(Paths.get("D:\\Test_Files\\signature"), digitalSignature);
		Files.write(Paths.get("D:\\Test_Files\\publickey"), keyPair.getPublic().getEncoded());
		
		System.out.println("completed");
		
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}

}
