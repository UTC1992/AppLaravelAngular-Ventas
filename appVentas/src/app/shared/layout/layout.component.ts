import {Component, OnDestroy} from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './layout.component.html'
})
export class LayoutComponent {

  
  constructor(
    public loginService: LoginService,
    private router:Router
  ){
    
  }

  logout(): void{
    let username = this.loginService.usuario.username;
    this.loginService.logout();
    swal('Logout', `Hola ${username}, has cerrado sesión con éxito!`, 'success');
    this.router.navigate(['/login']);
  }

}
