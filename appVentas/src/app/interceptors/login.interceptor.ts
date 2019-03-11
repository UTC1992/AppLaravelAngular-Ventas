import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { LoginService } from '../services/login.service';
import swal from 'sweetalert2';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class LoginInterceptor implements HttpInterceptor {

  constructor(
    private loginService: LoginService,
    private route: Router
  ){

  }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    
    return next.handle(req).pipe(
      catchError(e => {
        if(e.status == 401){
          if(this.loginService.isAuthenticated()){
            this.loginService.logout();
          }
          this.route.navigate(['/login']);
        }
    
        if(e.status == 403){
          swal('Acceso denergado', `Hola ${this.loginService.usuario.username} no tienes acceso a este recurso!`, 'warning');
          this.route.navigate(['/inicio']);
        }
        
        return throwError(e);
      })
    );
  }
}