import { Component, OnInit, TemplateRef  } from '@angular/core';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from 'src/app/models/usuario';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { LoginService } from '../../../services/login.service';
import swal from 'sweetalert2';

import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  formCreateUser: FormGroup;
  formEdit: FormGroup;
  public usuarios: Usuario[];

  titleModal: any;
  idEmpresa: any;
  usuario: Usuario;
  usuarioEdit: Usuario;

  tipoAccion: string;

  constructor(
    private usuarioService: UsuarioService,
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private modalService: BsModalService,
    public modalRef: BsModalRef,
    
  ) {
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;
    this.iniciarFormulario();
    
  }
  
  ngOnInit() {
    this.mostrarUsers();
  }
  
  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear un nuevo usuario";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del usuario";
    this.tipoAccion = "edit";
    this.usuarioService.getUsuarioById(id).subscribe(response =>{
      if(response != null){
        this.usuarioEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
      
    });
    
  }

  mostrarUsers(){
    this.usuarioService.getUsuarios().subscribe(response => {
      console.log(response);
      this.usuarios = response;
    });
  }

  iniciarFormulario(){
    this.formCreateUser = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.required],
      telefono: ['', Validators.required],
      direccion: ['', Validators.required],
      email: ['', Validators.required],
      enabled:['', Validators.required],
      username:['', Validators.required],
      password:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreateUser = this.formBuilder.group({
      nombre: [this.usuarioEdit.nombre, Validators.required],
      apellido: [this.usuarioEdit.apellido, Validators.required],
      dni: [this.usuarioEdit.dni, Validators.required],
      telefono: [this.usuarioEdit.telefono, Validators.required],
      direccion: [this.usuarioEdit.direccion, Validators.required],
      email: [this.usuarioEdit.email, Validators.required],
      enabled:[this.usuarioEdit.enabled, Validators.required],
      username:[this.usuarioEdit.username, Validators.required],
      password:["", Validators.required],
      idEmpresa:[this.usuarioEdit.idEmpresa, Validators.required]
    });
  }

  enviarDatosUsuario(){
    console.log(this.formCreateUser.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.usuarioService.create(this.formCreateUser.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Usuario guardado');
          this.mostrarUsers();
          this.cerrarModal();
          swal('Éxito', 'Usuario creado con exito!', 'info');
        } else {
          console.log('Usuario error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.usuarioService.edit(this.formCreateUser.value, this.usuarioEdit.id).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Usuario modificado');
          this.mostrarUsers();
          this.cerrarModal();
          swal('Éxito', 'Usuario editado con exito!', 'info');
        } else {
          console.log('Usuario error al editar');
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
      text: "No podrá revertir la eliminación",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar usuario!'
    }).then((result) => {
      if (result.value) {
        this.usuarioService.delete(id).subscribe(response => {
          console.log(response);
          if(response){
            swal(
              'Eliminado!',
              'El usuario a sido eliminado.',
              'success'
            )
            this.mostrarUsers();
          }
        });
      }
    })
  }

  

}
