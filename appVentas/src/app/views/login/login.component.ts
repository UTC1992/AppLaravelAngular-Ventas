import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  usuario: Usuario;

  constructor(
    private loginService:LoginService,
    private router:Router
  ) { 
    this.usuario = new Usuario();

  }

  ngOnInit() {
    if(this.loginService.isAuthenticated()){
      swal('Login', `Hola ${this.loginService.usuario.username} ya estas logueado!`, 'info');
      this.router.navigate(['/base']);
    }
  }

  login(){
    console.log(this.usuario);
    if(this.usuario.username == null || this.usuario.password == null){
      swal('Error Login', 'Email o password estan vacios!', 'error');
      return;
    }

    this.loginService.login(this.usuario).subscribe(response =>{
      console.log(response);

      //conversion a objeto para acceder a los datos del token
      //let datosToken = JSON.parse(atob(response.access_token.split(".")[1]));
      //console.log(datosToken);

      this.loginService.guardarUsuario(response.access_token);
      this.loginService.guardarToken(response.access_token);

      //llamamos al metodo getter para obtener datos del usuario
      let usuario = this.loginService.usuario;

      this.router.navigate(['/base']);
      swal('Login', `Hola ${usuario.nombre}, has iniciado sesión con éxito`, 'success');
    }, error =>{
      if(error.status == 400){
        swal('Error Login', 'Usuario o clave incorrectas!', 'error');
      }
    });
  }

}
