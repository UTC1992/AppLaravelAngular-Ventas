import { Producto } from './producto';

export class DetalleVenta{
    public idDetalleVenta: number;
	public cantidad: number;
	public costoDetalle: any;
	public totalDetalle: any;
    public ivaDetalle: any;
    public producto: Producto;
    
    public calcularImporte(): number {
        return this.cantidad * this.producto.precioVentaProducto;
      }
}