package performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.junit.ContiPerfSuiteRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import categories.IntegrationTest;
import dao.CategoriaDAO;
import dao.ICategoriaDAO;
import dao.IProductoDAO;
import dao.IUsuarioDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Compra;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import service.VentasService;

@Category(IntegrationTest.class)
public class ServicePerformanceTest {
	VentasService vs;
	private Usuario u;
	private Producto p;
	private Categoria c;
	private ProductoOrdenador po;
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	@Before
	public void setUp() {
		vs= new VentasService();
		
		u= new Usuario();
		u.setEmail("u@gmail.com");
		u.setPassword("u");
		
		p= new Producto();
		p.setId(1);
		p.setNombre("acer");
		
		c= new Categoria();
		c.setId(1);
		c.setNombre("ordenadores");
		
		po=new ProductoOrdenador();
		po.setRam(8);
		po.setCpu("intel i5");
		
	}
	
	@Test 
    @PerfTest(invocations = 1000, threads = 20)
    @Required(max = 3000, average = 1500)
	public void testLogIn() {	
		assertTrue(vs.logIn("u@gmail.com", "u"));
		assertFalse(vs.logIn(null, "a"));
		assertFalse(vs.logIn("a", "b"));
		assertFalse(vs.logIn("b", "b"));
	}
	
	@Test
	@PerfTest(invocations = 1000, threads = 20)
    @Required(max = 100000, average = 100000)
	public void testGetCategorias(){
		List<Categoria>cat=vs.getCategorias();
		assertEquals(vs.getCategorias().size(), 2);
	}
	
	@Test
	@PerfTest(invocations = 400, threads = 8)
    @Required(max = 2400, average = 200)
	public void testGetProductosOrdenador() {
		List<ProductoOrdenador> pos = vs.getProductosOrdenador();
		assertEquals(vs.getProductosOrdenador().size(),3);
	}
	
	
	
}
