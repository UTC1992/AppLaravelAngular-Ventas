import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';

import { ClienteService } from '../../../services/cliente.service';
import { LoginService } from '../../../services/login.service';
import { ProductoService } from '../../../services/producto.service';
import { TipoDocumentoService } from '../../../services/tipo-documento.service';
import { VentaService } from '../../../services/venta.service';
import { UsuarioService } from '../../../services/usuario.service';

import { Usuario } from 'src/app/models/usuario';
import { Cliente } from '../../../models/cliente';
import { DetalleVenta } from '../../../models/detalle-venta';
import { Producto } from '../../../models/producto';
import { TipoDocumento } from '../../../models/tipo-documento';
import { TipoPago } from '../../../models/tipo-pago';
import { Venta } from '../../../models/venta';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';
import { Observable } from 'rxjs';
import { map, flatMap, startWith } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material';

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-inicio',
  templateUrl: 'inicio.component.html',
  styleUrls: ['./inicio.component.scss']
})
export class InicioComponent {

  displayedColumns: string[] = [
    'idVenta', 'serieVenta', 'numeroVenta',
    'totalVenta', 'descuentoVenta',
    'subTotalVenta', 'ivaVenta', 
    'totalPagarVenta', 'estadoVenta',
    'puntoVentaId', 'tipoDocumento',
    'usuario', 'cliente', 'detalleVenta',
    'total', 'acciones'
  ];
  dataSource = new MatTableDataSource();

  formCreate: FormGroup;
  formEdit: FormGroup;
  public ventas: Venta[];
  ventaEdit: Venta;
  venta: Venta = new Venta();
  tipoDocumentos: TipoDocumento[];
  detalleVenta: DetalleVenta[] = [];

  clienteFactura: Cliente = new Cliente();
  clienteAuxiliar: Cliente = new Cliente();
  usuarioFactura: Usuario = new Usuario();
  documentoFactura: TipoDocumento = new TipoDocumento();

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  provincias: any[];

  autocompleteControl = new FormControl();
  productosFiltrados: Observable<Producto[]>;

  cedulaRuc: any;
  clienteNombreCompleto: any;

  codigoProducto: string;

  fecha = new Date();
  fechaActual = this.fecha.getDate()+"-"+(this.fecha.getMonth() +1)+"-"+this.fecha.getFullYear();

  constructor(
    private ventaService: VentaService,
    private clienteService: ClienteService,
    private productoService: ProductoService,
    private tipoDocService: TipoDocumentoService,
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private modalService: BsModalService,
    public modalRef: BsModalRef,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private usuarioService: UsuarioService
  ) {
    this.iniciarFormulario();
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;

    this.activatedRoute.queryParams.subscribe(res =>{
      console.log(res.venta);
      if(res.venta > 0){
        this.obtenerDatosVenta(parseInt(res.venta));
      }
    });
  }

  ngOnInit() {
    this.subTitlePagina = "Ventas";
    this.obtenerTiposDocumento();
    this.obtenerUsuario();

    this.productosFiltrados = this.autocompleteControl.valueChanges
      .pipe(
        map(value => typeof value === 'string' ? value : value.nombre),
        flatMap(value => value ? this._filter(value) : [])
      );
  }
  
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  private _filter(value: string): Observable<Producto[]> {
    const filterValue = value.toLowerCase();

    return this.productoService.filtrarProductosNombre(filterValue);
  }

  mostrarNombre(producto?: Producto): string | undefined {
    return producto ? producto.nombreProducto : undefined;
  }

  seleccionarProducto(event: MatAutocompleteSelectedEvent): void {
    let producto = event.option.value as Producto;
    console.log(producto);

    if (this.existeItem(producto.idProducto)) {
      this.incrementaCantidad(producto.idProducto);
    } else {
      let nuevoItem = new DetalleVenta();
      nuevoItem.producto = producto;
      nuevoItem.cantidad = 1;
      nuevoItem.costoDetalle = producto.precioVentaProducto;
      nuevoItem.totalDetalle = producto.precioVentaProducto * nuevoItem.cantidad;
      nuevoItem.ivaDetalle = parseFloat(nuevoItem.totalDetalle) * 0.12;
      console.log(nuevoItem);
      this.detalleVenta.push(nuevoItem);
    }

    this.iniciarFormulario();
    this.autocompleteControl.setValue('');
    event.option.focus();
    event.option.deselect();

  }

  actualizarCantidad(id: number, event: any): void {
    let cantidad: number = event.target.value as number;

    if (cantidad == 0) {
      return this.eliminarItemFactura(id);
    }

    this.detalleVenta = this.detalleVenta.map((item: DetalleVenta) => {
      if (id === item.producto.idProducto) {
        item.cantidad = parseInt(cantidad+'');
        item.costoDetalle = item.producto.precioVentaProducto;
        item.totalDetalle = item.producto.precioVentaProducto * item.cantidad;
        item.ivaDetalle = parseFloat(item.totalDetalle) * 0.12;
      }
      this.iniciarFormulario();
      return item;
    });
  }

  existeItem(id: number): boolean {
    let existe = false;
    for(let i=0; i < this.detalleVenta.length; i++ ){
      if (id === this.detalleVenta[i].producto.idProducto) {
        existe = true;
      }
    }
    return existe;
  }

  incrementaCantidad(id: number): void {
    this.detalleVenta = this.detalleVenta.map((item: DetalleVenta) => {
      if (id === item.producto.idProducto) {
        ++item.cantidad;
      }
      return item;
    });
  }

  eliminarItemFactura(id: number): void {
    this.detalleVenta = this.detalleVenta.filter((item: DetalleVenta) => id !== item.producto.idProducto);
  }

  obtenerTiposDocumento(){
    this.tipoDocService.getTipoDocumentos().subscribe(res =>{
      console.log(res);
      this.tipoDocumentos = res;
    }, error =>{
      this.tipoDocumentos = [];
    });
  }
  
  buscarCliente(cedula: string){
    console.log("Cambio");
    this.clienteService.getClienteByCedula(cedula).subscribe(res =>{
      console.log(res);
      this.clienteFactura = res;
      this.clienteNombreCompleto = this.clienteFactura.nombres + " " + this.clienteFactura.apellidos;
    },error => {
      //console.log(error);
      this.clienteFactura = this.clienteAuxiliar;
      this.clienteNombreCompleto = "";
    });
  }

  buscarProductoCodigo(codigo: string){
    console.log("Cambio");
    this.productoService.filtrarProductosCodigo(codigo).subscribe(res =>{
      console.log(res);
      this.insertarProducto(res[0]);
      this.codigoProducto = "";
    },error => {
      //console.log(error);
      this.codigoProducto = "";
      this.clienteFactura = this.clienteAuxiliar;
    });
  }

  insertarProducto(item: Producto): void {
    let producto = item;
    console.log(producto);

    if (this.existeItem(producto.idProducto)) {
      this.incrementaCantidad(producto.idProducto);
    } else {
      let nuevoItem = new DetalleVenta();
      nuevoItem.producto = producto;
      nuevoItem.cantidad = 1;
      console.log(nuevoItem);
      this.detalleVenta.push(nuevoItem);
    }

    this.autocompleteControl.setValue('');
  }

  obtenerUsuario(){
    this.usuarioService.getUsuarioById(this.usuario.id).subscribe(response =>{
      this.usuarioFactura = response;
    });
  }

  iniciarFormulario(){
    this.venta.detalleVenta = this.detalleVenta;
    this.formCreate = this.formBuilder.group({
      cliente: [this.clienteFactura, Validators.required],
      idEmpresa: [this.idEmpresa, Validators.required],
      ivaVenta: [parseFloat(this.venta.iva12()), Validators.required],
      subTotalVenta: [parseFloat(this.venta.subTotal()), Validators.required],
      totalVenta: [parseFloat(this.venta.iva12()) + parseFloat(this.venta.subTotal()), Validators.required],
      serieVenta: ['101', Validators.required],
      numeroVenta: ['001', Validators.required],
      descuentoVenta: [0, Validators.required],
      totalPagarVenta: [parseFloat(this.venta.iva12()) + parseFloat(this.venta.subTotal()), Validators.required],
      estadoVenta: [1, Validators.required],
      observacion: ["N/A", Validators.required],
      puntoVentaId: [1, Validators.required],
      usuario: [this.usuarioFactura, Validators.required],
      tipoDocumento: [this.documentoFactura, Validators.required],
      detalleVenta: [this.detalleVenta, Validators.required],
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      cliente: [this.ventaEdit.cliente, Validators.required],
      idEmpresa: [this.venta.idEmpresa, Validators.required],
      ivaVenta: [this.venta.iva12, Validators.required],
      subTotalVenta: [this.venta.subTotalVenta, Validators.required],
      totalVenta: [this.venta.totalVenta, Validators.required],
      serieVenta: [this.venta.serieVenta, Validators.required],
      numeroVenta: [this.venta.numeroVenta, Validators.required],
      descuentoVenta: [this.venta.descuentoVenta, ],
      totalPagarVenta: [this.venta.totalPagarVenta, Validators.required],
      estadoVenta: [this.venta.estadoVenta, Validators.required],
      observacion: [this.venta.observacion, ],
      puntoVentaId: [this.venta.puntoVentaId, Validators.required],
      usuario: [this.venta.usuario, Validators.required],
      tipoDocumento: [this.venta.tipoDocumento, Validators.required],
      detalleVenta: [this.detalleVenta, Validators.required],
    });
  }

  crearVenta(): void{
    this.venta.cliente = this.clienteFactura;
    this.venta.ivaVenta = parseFloat(this.venta.iva12());
    this.venta.subTotalVenta = parseFloat(this.venta.subTotal());
    this.venta.totalVenta = parseFloat(this.venta.iva12()) + parseFloat(this.venta.subTotal());
    this.venta.serieVenta = '101';
    this.venta.numeroVenta = '001';
    this.venta.descuentoVenta = 0;
    this.venta.totalPagarVenta = parseFloat(this.venta.iva12()) + parseFloat(this.venta.subTotal());
    this.venta.estadoVenta = 1;
    this.venta.observacion = "N/A";
    this.venta.puntoVentaId = 1;
    this.venta.idEmpresa = this.usuario.idEmpresa;
    this.venta.usuario = this.usuarioFactura;
    this.venta.tipoDocumento = this.documentoFactura;
    
    this.iniciarFormulario();
    console.log(this.formCreate.value);

    this.ventaService.create(this.formCreate.value).subscribe(res =>{
      console.log(res);
    }, error => {
      console.log(error);
    });

  }

  //EDITAR VENTA INICIO
  obtenerDatosVenta(id: number): void{
    this.ventaService.getVentaById(id).subscribe(res =>{
      console.log(res);
      this.venta = res;
      this.clienteFactura = this.venta.cliente;
      this.cedulaRuc = this.clienteFactura.cedula;
      this.clienteNombreCompleto = this.clienteFactura.nombres + " " + this.clienteFactura.apellidos;
      this.documentoFactura = this.venta.tipoDocumento;
      this.detalleVenta = this.venta.detalleVenta;
    });
  }
  //EDITAR VENTA FIN

}
