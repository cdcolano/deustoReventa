package server;

import static org.junit.Assert.fail;

import org.junit.Test;

public class MainServerTest {
	
	@Test
	public void testMain() {
		try {
			String[] a= new String[3];
			MainServer ms= new MainServer();
			MainServer.main(a);
		}catch(Exception e) {
			fail();
		}
	}
}
