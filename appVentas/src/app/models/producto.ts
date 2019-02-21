import { CategoriaProducto } from './categoria-producto';
import { TipoProducto } from './tipo-producto';

export class Producto{
    public idProducto: number;
    public idEmpresa: number;
	public codigoProducto: string;
	public nombreProducto: string;
	public descripcionProducto: string;
	public stockProducto: number;
	public stockMinProducto: number;
	public precioCostoProducto: any;
	public precioVentaProducto: any;
	public utilidadProducto: any;
	public estadoProducto: boolean;
    public observaciones: string;
    public categoria: CategoriaProducto;
    public tipoProducto: TipoProducto;
}