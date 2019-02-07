import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from 'src/app/models/usuario';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  formCreateUser: FormGroup;
  formEdit: FormGroup;
  public usuarios: Usuario[];

  constructor(
    private usuarioService: UsuarioService,
    private formBuilder: FormBuilder,
  ) { 

    this.iniciarFormulario();
  }

  ngOnInit() {
    this.mostrarUsers();
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
      password:['', Validators.required]
    });
  }

  crearUsuario(){
    console.log(this.formCreateUser.value);
    this.usuarioService.create(this.formCreateUser.value).subscribe(response => {
      console.log(response);
      if(response){
        console.log('Usuario guardado');
      } else {
        console.log('Usuario error al guardar');
      }
      this.ngOnInit();
    });
  }

}
