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

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-venta',
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.scss']
})
export class VentaComponent implements OnInit {

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

  clienteFactura: Cliente = new Cliente();
  clienteAuxiliar: Cliente = new Cliente();

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
    private activatedRoute: ActivatedRoute
  ) {
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;
  }

  ngOnInit() {
    this.subTitlePagina = "Ventas";
    this.mostrarVentas();
  }

  mostrarVentas(){
    this.ventaService.getVentas().subscribe(response => {
      console.log(response);
      this.dataSource = new MatTableDataSource(response);
      //this.ventas = response;
    }, error => {
      this.ventas = [];
      console.log(error.error.mensaje);
    });
  }
  
  mostrarFormEdit(id: number): void{
    this.ventaService.getVentaById(id).subscribe(res => {
      console.log(res);
      this.venta = res;
      this.router.navigate(['/base/inicio'], { queryParams : { venta : id } } );

    }, error =>{
      console.log(error);
    });
  }

}
