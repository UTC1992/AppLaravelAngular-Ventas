import { TipoDocumento } from './tipo-documento';
import { Usuario } from './usuario';
import { Cliente } from './cliente';
import { DetalleVenta } from './detalle-venta';

export class Venta{
	public idVenta: number;
	public serieVenta: string;
	public numeroVenta: string;
	public totalVenta: any;
	public descuentoVenta: any;
	public subTotalVenta: any;
	public ivaVenta: any;
	public totalPagarVenta: any;
	public estadoVenta: number;
	public observacion: string;
	public puntoVentaId: number;
	public tipoDocumento: TipoDocumento;
	public usuario: Usuario;
	public cliente: Cliente;
	public idEmpresa: number;
	public detalleVenta: DetalleVenta[] = [];
	public total: any;

	subTotal(): any {
		this.total = 0;
		this.detalleVenta.forEach((item: DetalleVenta) => {
		  this.total += item.calcularImporte();
		});
		return parseFloat(this.total).toFixed(2);
	}

	iva12(): any{
		let subTotal = this.subTotal();
		return (parseFloat(subTotal)*0.12).toFixed(2);
	}

	totalFinal(): any{
		let subTotal = this.subTotal();
		let resTotal =  ((parseFloat(subTotal)*0.12) + parseFloat(subTotal));
		return resTotal.toFixed(2);
	}
}