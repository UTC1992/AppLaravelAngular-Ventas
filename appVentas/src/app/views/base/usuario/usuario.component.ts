import { Component, OnInit, TemplateRef  } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { LoginService } from '../../../services/login.service';
import { UsuarioService } from '../../../services/usuario.service';

import { Usuario } from 'src/app/models/usuario';
import { Rol } from '../../../models/rol';

import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';
import swal from 'sweetalert2';

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  displayedColumns: string[] = [
    'id', 'dni', 'nombre',
    'celular', 'email',
    'username', 'roles', 'acciones'
  ];
  dataSource = new MatTableDataSource();

  formCreateUser: FormGroup;
  formRol: FormGroup;
  formEdit: FormGroup;
  public usuarios: Usuario[];

  titleModal: any;
  idEmpresa: any;
  usuario: Usuario;
  usuarioEdit: Usuario;
  idUsuario: number;

  subTitlePagina: string;

  tipoAccion: string;
  titleRol: any;
  rolesList: any[] = [];

  rolUser: boolean;
  rolAdmin: boolean;
  rolRoot: boolean;

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
    this.iniciarFormularioRol();
    
  }
  
  ngOnInit() {
    this.subTitlePagina = "Usuarios";
    this.mostrarUsers();
    this.llenarRoles();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
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
      this.dataSource = new MatTableDataSource(response);
      //this.usuarios = response;
    }, error => {
      this.usuarios = [];
      console.log(error.error.mensaje);
    });
  }

  iniciarFormulario(){
    this.formCreateUser = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.required],
      celular: ['', Validators.required],
      telefono: [''],
      direccion: ['', Validators.required],
      email: ['', Validators.required],
      enabled:['', Validators.requiredTrue],
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
      celular: [this.usuarioEdit.celular, Validators.required],
      telefono: [this.usuarioEdit.telefono],
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
          swal('Éxito', 'Usuario creado con exito!', 'success');
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
          swal('Éxito', 'Usuario editado con exito!', 'success');
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
      confirmButtonText: 'Si, eliminar Usuario!'
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
    });
  }

  iniciarFormularioRol(){
    this.formRol = this.formBuilder.group({
      rol: ['', Validators.required],
    });
  }

  llenarRoles(){
    this.rolesList = [];
    this.rolesList.push({id: 1, authority: "ROLE_USER", add: true, del: false});
    this.rolesList.push({id: 2, authority: "ROLE_ADMIN", add: true, del: false});
    this.rolesList.push({id: 3, authority: "ROLE_ROOT", add: true, del: false});
  }

  validarRoles(usuario: Usuario){
    console.log("Roles asignados a usuario ==> ");
    console.log(usuario.roles);
    for(var i = 0; i < usuario.roles.length; i++){
      for(var j = 0; j < this.rolesList.length; j++){
        if(usuario.roles[i]['authority'] == this.rolesList[j]['authority']){
          this.rolesList[j]['add'] = false; 
          this.rolesList[j]['del'] = true; 
        }
      }
    }
  }

  mostrarRoles(templateRol: TemplateRef<any>, usuario: Usuario) {
    this.llenarRoles();
    this.validarRoles(usuario);
    console.log("Lista de roles de app ==> ");
    console.log(this.rolesList);
    this.idUsuario = usuario.id;
    this.titleRol = "Eliga el rol a asignar:";
    this.modalRef = this.modalService.show(templateRol);
  }

  asignarRol(rol: Rol){
      this.usuarioService.addRol(rol, this.idUsuario).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Rol asignado');
          this.mostrarUsers();
          this.llenarRoles();
          this.cerrarModal();
          swal('Éxito', 'Rol asignado con exito!', 'success');
        } else {
          console.log('Rol error al asignar');
        }
      });
  }

  confirmarLimpiarRoles(){
    swal({
      title: '¿Está seguro?',
      text: "Se eliminarán todos los roles asignados",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar Roles!'
    }).then((result) => {
      if (result.value) {
        this.eliminarRol();
      }
    });
  }

  eliminarRol(){
    this.usuarioService.deleteRol(this.idUsuario).subscribe(response => {
      console.log(response);
      if(response){
        console.log('Rol eliminado');
        this.mostrarUsers();
        this.llenarRoles();
        this.cerrarModal();
        swal('Roles Eliminados', 'Roles eliminados con exito!', 'success');
      } else {
        console.log('Rol error al eliminar');
      }
    });
}

}
