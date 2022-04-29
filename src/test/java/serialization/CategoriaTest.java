package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class CategoriaTest {
	private Categoria cat;
	
	@Before
	public void setUp() {
		cat= new Categoria();
	}
	
/*	@Before
	public void enhance() {	
		Process process;
		try {
			process = Runtime.getRuntime().exec("cmd.exe /c mvn datanucleus:enhance");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
*/	
/*	@After
	public void clean() {
		Process process;
		try {
			process = Runtime.getRuntime().exec("cmd.exe /c mvn clean compile");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/	
	
	@Test
	public void testID() {
		cat.setId(1);
		assertEquals(cat.getId(),1);
	}
	
	
	@Test
	public void testNombre() {
		cat.setNombre("Prueba");
		assertTrue(cat.getNombre().contentEquals("Prueba"));
	}
	
	@Test
	public void testtoString() {
		cat.setNombre("Prueba");
		assertTrue(cat.toString().contentEquals(cat.getNombre()));
	}
	
	
	
}
