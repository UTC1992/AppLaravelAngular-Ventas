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
  }

  login(){
    console.log(this.usuario);
    if(this.usuario.username == null || this.usuario.password == null){
      swal('Error Login', 'Email o password estan vacios!', 'error');
      return;
    }

    this.loginService.login(this.usuario).subscribe(res =>{
      console.log(res);
      this.router.navigate(['/base']);
      swal('Login', `Hola ${res.nombre}, has iniciado sesión con éxito`, 'success');
    });
  }

}
