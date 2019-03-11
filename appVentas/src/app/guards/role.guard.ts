import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../services/login.service';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    private loginService: LoginService,
    private router: Router
  ){

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
    if(!this.loginService.isAuthenticated()){
      this.router.navigate(['/login']);
      return false;
    }

    let role = next.data['role'] as string;
    console.log(role);
    if(this.loginService.hasRole(role) ){
      return true;
    }
    swal('Acceso denergado', `Hola ${this.loginService.usuario.username} no tienes acceso a este recurso!`, 'warning');
    this.router.navigate(['/inicio']);
    return true;
  }
}
