import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { ProductoService } from '../../../services/producto.service';
import { CategoriaProductoService } from '../../../services/categoria-producto.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { Producto } from '../../../models/producto';
import { TipoProducto } from '../../../models/tipo-producto';
import { CategoriaProducto } from '../../../models/categoria-producto';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.scss']
})
export class ProductoComponent implements OnInit {

  displayedColumns: string[] = [
    'codigoProducto', 
    'nombreProducto', 'descripcionProducto',
    'stockProducto', 'stockMinProducto', 
    'precioCostoProducto', 
    'precioVentaProducto', 'utilidadProducto', 
    'estadoProducto', 
    'nombreCategoria', 
    'tipoProducto', 'acciones' 
  ];
  dataSource = new MatTableDataSource();

  formCreate: FormGroup;
  formEdit: FormGroup;
  public productos: Producto[];
  productoEdit: Producto;
  public categorias: CategoriaProducto[];
  public tipoProductos: TipoProducto[];

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;


  constructor(
    private productoServices: ProductoService,
    private categoriaService: CategoriaProductoService,
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private modalService: BsModalService,
    public modalRef: BsModalRef,
  ) { 
    this.iniciarFormulario();
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;
  }

  ngOnInit() {
    this.subTitlePagina = "Productos";
    this.mostrarProductos();
    this.obtenerCategorias();
    this.obtenerTipoProductos();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      codigoProducto: ['', Validators.required],
      nombreProducto: ['', Validators.required],
      descripcionProducto: ['', Validators.required],
      stockProducto:['', Validators.required],
      stockMinProducto:['', Validators.required],
      precioCostoProducto:['', Validators.required],
      precioVentaProducto:['', Validators.required],
      utilidadProducto:['', Validators.required],
      estadoProducto:['', Validators.required],
      observaciones:['', Validators.required],
      categoria:['', Validators.required],
      tipoProducto:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      codigoProducto: [this.productoEdit.codigoProducto, Validators.required],
      nombreProducto: [this.productoEdit.nombreProducto, Validators.required],
      descripcionProducto: [this.productoEdit.descripcionProducto, Validators.required],
      stockProducto:[this.productoEdit.stockProducto, Validators.required],
      stockMinProducto:[this.productoEdit.stockMinProducto, Validators.required],
      precioCostoProducto:[this.productoEdit.precioCostoProducto, Validators.required],
      precioVentaProducto:[this.productoEdit.precioVentaProducto, Validators.required],
      utilidadProducto:[this.productoEdit.utilidadProducto, Validators.required],
      estadoProducto:[this.productoEdit.estadoProducto, Validators.required],
      observaciones:[this.productoEdit.observaciones, Validators.required],
      categoria:[this.productoEdit.categoria, Validators.required],
      tipoProducto:[this.productoEdit.tipoProducto,Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarProductos(){
    this.productoServices.getProductos().subscribe(response => {
      console.log(response);
      this.dataSource = new MatTableDataSource(response);
      //this.productos = response;
    }, error => {
      this.productos = [];
      console.log(error.error.mensaje);
    });
  }

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.obtenerCategorias();
    this.obtenerTipoProductos();
    this.tipoAccion = "create";
    this.titleModal = "Crear un nuevo producto";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del producto";
    this.tipoAccion = "edit";
    this.productoServices.getProductoById(id).subscribe(response =>{
      if(response != null){
        this.productoEdit = response;
        this.iniciarFormularioEdit();
        this.modificarCategoriasParaEditar();
        this.modificarTipoProductosParaEditar();
        this.modalRef = this.modalService.show(template);
      }
      
    });
    
  }

  obtenerCategorias(){
    this.categoriaService.getCategorias().subscribe(response => {
      console.log(response);
      this.categorias = response;
    }, error => {
      this.categorias = [];
      console.log(error.error.mensaje);
    });
  }

  modificarCategoriasParaEditar(){
    for(let i = 0; i < this.categorias.length; i++){
      //console.log(this.categorias[i].nombreCategoria);
      if(this.productoEdit.categoria.idCategoriaProducto == this.categorias[i].idCategoriaProducto){
        this.categorias[i].elegido = true;
      } else {
        this.categorias[i].elegido = false;
      }
    }
  }

  obtenerTipoProductos(){
    this.productoServices.getTipoProductos().subscribe(response => {
      console.log(response);
      this.tipoProductos = response;
    }, error => {
      this.tipoProductos = [];
      console.log(error.error.mensaje);
    });
  }

  modificarTipoProductosParaEditar(){
    for(let i = 0; i < this.tipoProductos.length; i++){
      //console.log(this.categorias[i].nombreCategoria);
      if(this.productoEdit.tipoProducto.id == this.tipoProductos[i].id){
        this.tipoProductos[i].elegido = true;
      } else {
        this.tipoProductos[i].elegido = false;
      }
    }
  }

  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.productoServices.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Producto guardado');
          this.mostrarProductos();
          this.cerrarModal();
          swal('Éxito', 'Producto creado con exito!', 'success');
        } else {
          console.log('Producto error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.productoServices.edit(this.formCreate.value, this.productoEdit.idProducto).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Producto modificado');
          this.mostrarProductos();
          this.cerrarModal();
          swal('Éxito', 'Producto modificado con exito!', 'success');
        } else {
          console.log('Producto error al modificar');
        }
      });
    }
  }

  cerrarModal(){
    this.modalRef.hide();
  }

  confirmarEliminar(id){
    swal({
      title: '¿Está seguro?',
      text: "Se eliminará el producto",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarProducto(id);
      }
    });
  }

  eliminarProducto(id){
    this.productoServices.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'El Producto a sido eliminado.',
          'success'
        )
        this.mostrarProductos();
      }
    });
  }

}
