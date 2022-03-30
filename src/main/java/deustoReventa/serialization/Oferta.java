package deustoReventa.serialization;

import java.sql.Date;

public class Oferta {
	private int id;
	private double cantidad;
	private Date fecha;
	private boolean estado;
	private Producto producto;
	private Usuario usuario_emisor;
}
