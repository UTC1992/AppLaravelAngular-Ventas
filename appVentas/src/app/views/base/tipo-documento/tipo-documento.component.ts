import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { TipoDocumentoService } from '../../../services/tipo-documento.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { TipoDocumento } from '../../../models/tipo-documento';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-tipo-documento',
  templateUrl: './tipo-documento.component.html',
  styleUrls: ['./tipo-documento.component.scss']
})
export class TipoDocumentoComponent implements OnInit {
  
  formCreate: FormGroup;
  formEdit: FormGroup;
  public tiposDocumentos: TipoDocumento[];
  tipoDocEdit: TipoDocumento;

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  constructor(
    private tipoDocService: TipoDocumentoService,
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
    this.subTitlePagina = "Tipos de documentos / comprobantes";
    this.mostrarTiposDocumento();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombreDocumento: ['', Validators.required],
      descripcion: ['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      nombreDocumento: [this.tipoDocEdit.nombreDocumento, Validators.required],
      descripcion: [this.tipoDocEdit.descripcion, Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarTiposDocumento(){
    this.tipoDocService.getTipoDocumentos().subscribe(response => {
      console.log(response);
      this.tiposDocumentos = response;
    }, error => {
      this.tiposDocumentos = [];
      console.log(error.error.mensaje);
    });
  }  

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear nuevo tipo de documento";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del tipo de documento";
    this.tipoAccion = "edit";
    this.tipoDocService.getTipoDocumentoById(id).subscribe(response =>{
      if(response != null){
        this.tipoDocEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
    });
  }
  
  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.tipoDocService.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Tipo de documento guardado');
          this.mostrarTiposDocumento();
          this.cerrarModal();
          swal('Éxito', 'Tipo de documento creado con exito!', 'success');
        } else {
          console.log('Tipo de documento error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.tipoDocService.edit(this.formCreate.value, this.tipoDocEdit.idTipoDocumento).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Tipo de documento modificado');
          this.mostrarTiposDocumento();
          this.cerrarModal();
          swal('Éxito', 'Tipo de documento modificado con exito!', 'success');
        } else {
          console.log('Tipo de documento error al modificar');
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
      text: "Se eliminará el tipo de documento",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarTipoDocumento(id);
      }
    });
  }

  eliminarTipoDocumento(id){
    this.tipoDocService.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'El tipo de documento a sido eliminado.',
          'success'
        )
        this.mostrarTiposDocumento();
      }
    });
  }

}
