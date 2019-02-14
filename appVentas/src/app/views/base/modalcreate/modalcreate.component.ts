import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { LoginService } from '../../../services/login.service';
import { UsuarioService } from '../../../services/usuario.service';
import swal from 'sweetalert2';
import { from } from 'rxjs';

import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-modalcreate',
  templateUrl: './modalcreate.component.html',
  styleUrls: ['./modalcreate.component.scss']
})
export class ModalcreateComponent implements OnInit {

  formCreateUser: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private usuarioService: UsuarioService,
    public modalRef: BsModalRef,
  ) {
    this.iniciarFormulario();

   }

  ngOnInit() {

  }

  iniciarFormulario(){
    let usuario = this.loginService.usuario;
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
      idEmpresa:[usuario.idEmpresa, Validators.required]
    });
  }

  crearUsuario(){
    console.log(this.formCreateUser.value);
    this.usuarioService.create(this.formCreateUser.value).subscribe(response => {
      console.log(response);
      if(response){
        console.log('Usuario guardado');
        this.iniciarFormulario();
        
        this.cerrarModal();
        swal('Éxito', 'Usuario creado con exito!', 'info');
      } else {
        console.log('Usuario error al guardar');
      }
    });
  }

  cerrarModal(){
    this.modalRef.hide();
  }

}
