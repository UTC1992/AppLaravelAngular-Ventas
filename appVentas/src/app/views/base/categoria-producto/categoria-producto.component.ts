import { Component, OnInit, TemplateRef  } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { LoginService } from '../../../services/login.service';
import { CategoriaProductoService } from '../../../services/categoria-producto.service';

import { Usuario } from 'src/app/models/usuario';
import { CategoriaProducto } from '../../../models/categoria-producto';

import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';
import swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-producto',
  templateUrl: './categoria-producto.component.html',
  styleUrls: ['./categoria-producto.component.scss']
})
export class CategoriaProductoComponent implements OnInit {

  formCreate: FormGroup;
  formEdit: FormGroup;
  public categorias: CategoriaProducto[];
  categoriaEdit: CategoriaProducto;

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  constructor(
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
    this.subTitlePagina = "Categorías para los productos";
    this.mostrarCategorias();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombreCategoria: ['', Validators.required],
      descripcionCategoria: ['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      nombreCategoria: [this.categoriaEdit.nombreCategoria, Validators.required],
      descripcionCategoria: [this.categoriaEdit.descripcionCategoria, Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarCategorias(){
    this.categoriaService.getCategorias().subscribe(response => {
      console.log(response);
      this.categorias = response;
    });
  }  

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear nueva categoría";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del punto de venta";
    this.tipoAccion = "edit";
    this.categoriaService.getPuntoVentaById(id).subscribe(response =>{
      if(response != null){
        this.categoriaEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
    });
  }
  
  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.categoriaService.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Categoria guardada');
          this.mostrarCategorias();
          this.cerrarModal();
          swal('Éxito', 'Categoría creada con exito!', 'success');
        } else {
          console.log('Categoria error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.categoriaService.edit(this.formCreate.value, this.categoriaEdit.idCategoriaProducto).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Categoria actualizada');
          this.mostrarCategorias();
          this.cerrarModal();
          swal('Éxito', 'Categoría modificada con exito!', 'success');
        } else {
          console.log('Categoria error al modificar');
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
      text: "Se eliminará la categoría",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarCategoria(id);
      }
    });
  }

  eliminarCategoria(id){
    this.categoriaService.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'La categoría a sido eliminada.',
          'success'
        )
        this.mostrarCategorias();
      }
    });
  }

}
