package trong.lixco.com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.primefaces.util.Base64;

import trong.lixco.com.entity.Account;

public class Encode {
	public static Object fromString(String s) throws IOException, ClassNotFoundException {
		byte[] data = Base64.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

	/** Write the object to a Base64 string. */
	public static String toString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return Base64.encodeToString(baos.toByteArray(), false);
	}
public static void main(String[] args) {
	Account account=new Account();
	account.setUserName("oghin");
	account.setPassword("admin");
	System.out.println("Start");
	try {
	String result=	toString(account);
	System.out.println(result);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
