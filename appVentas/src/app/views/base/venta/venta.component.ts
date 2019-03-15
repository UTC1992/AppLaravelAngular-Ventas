import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';

import { ClienteService } from '../../../services/cliente.service';
import { LoginService } from '../../../services/login.service';
import { ProductoService } from '../../../services/producto.service';
import { TipoDocumentoService } from '../../../services/tipo-documento.service';
import { VentaService } from '../../../services/venta.service';

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


@Component({
  selector: 'app-venta',
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.scss']
})
export class VentaComponent implements OnInit {

  formCreate: FormGroup;
  formEdit: FormGroup;
  public ventas: Venta[];
  ventaEdit: Venta;
  venta: Venta = new Venta();

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  provincias: any[];

  autocompleteControl = new FormControl();
  productosFiltrados: Observable<Producto[]>;

  cedulaRuc: any;

  codigoProducto: string;

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
    private activatedRoute: ActivatedRoute
  ) {
    this.iniciarFormulario();
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;
  }

  ngOnInit() {
    this.subTitlePagina = "Ventas";
    this.mostrarVentas();
    
    this.productosFiltrados = this.autocompleteControl.valueChanges
      .pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.codigoProducto),
        flatMap(value => value ? this._filter(value) : [])
      );
  }

  buscarProducto(codigo: string){
    this.productoService.filtrarProductos(codigo).subscribe(res =>{
      console.log(res[0]);
      this.seleccionarProducto(res[0]);
      
    });
  }

  private _filter(value: string): Observable<Producto[]> {
    const filterValue = value;
    console.log(filterValue);
    return this.productoService.filtrarProductos(filterValue);
  }

  mostrarNombre(producto?: Producto): string | undefined {
    return producto ? producto.nombreProducto : undefined;
  }

  seleccionarProducto(item: Producto): void {
    let producto = item;
    console.log(producto);

    if (this.existeItem(producto.idProducto)) {
      this.incrementaCantidad(producto.idProducto);
    } else {
      let nuevoItem = new DetalleVenta();
      nuevoItem.producto = producto;
      nuevoItem.cantidad = 1;
      console.log(nuevoItem);
      this.venta.detalleVenta.push(nuevoItem);
    }

    this.autocompleteControl.setValue('');
    //event.option.focus();
    //event.option.deselect();

  }

  actualizarCantidad(id: number, event: any): void {
    let cantidad: number = event.target.value as number;

    if (cantidad == 0) {
      return this.eliminarItemFactura(id);
    }

    this.venta.detalleVenta = this.venta.detalleVenta.map((item: DetalleVenta) => {
      if (id === item.producto.idProducto) {
        item.cantidad = cantidad;
      }
      return item;
    });
  }

  existeItem(id: number): boolean {
    let existe = false;
    this.venta.detalleVenta.forEach((item: DetalleVenta) => {
      if (id === item.producto.idProducto) {
        existe = true;
      }
    });
    return existe;
  }

  incrementaCantidad(id: number): void {
    this.venta.detalleVenta = this.venta.detalleVenta.map((item: DetalleVenta) => {
      if (id === item.producto.idProducto) {
        ++item.cantidad;
      }
      return item;
    });
  }

  eliminarItemFactura(id: number): void {
    this.venta.detalleVenta = this.venta.detalleVenta.filter((item: DetalleVenta) => id !== item.producto.idProducto);
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      serieVenta: ['', Validators.required],
      numeroVenta: ['', Validators.required],
      totalVenta: ['', Validators.required],
      cedula:['', Validators.required],
      descuentoVenta:['', Validators.required],
      subTotalVenta:['', Validators.required],
      ivaVenta:['', Validators.required],
      totalPagarVenta:['', Validators.required],
      estadoVenta:['', ],
      observacion:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarVentas(){
    this.ventaService.getVentas().subscribe(response => {
      console.log(response);
      this.ventas = response;
    }, error => {
      this.ventas = [];
      console.log(error.error.mensaje);
    });
  }

  openModalCreate(template: TemplateRef<any>) {
    this.tipoAccion = "create";
    this.titleModal = "Nueva Venta";
    this.modalRef = this.modalService.show(template);
  }
  
  buscarCliente(){
    console.log("Cambio");
  }

}
