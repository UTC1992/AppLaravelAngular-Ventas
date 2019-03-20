import {Component, OnDestroy} from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
  .pipe(
  map(result => result.matches)
  );
  
  constructor(
    private breakpointObserver: BreakpointObserver,
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
