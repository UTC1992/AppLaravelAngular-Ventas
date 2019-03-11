import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { httpFactory } from '@angular/http/src/http_module';
import { Usuario } from '../models/usuario';
import { Rol } from '../models/rol';

import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private urlEndPoint: string = 'http://localhost:8090/api';
  
  private httpHeaders = new HttpHeaders({
    'Content-Type':'application/json'
  });

  constructor(
    private http: HttpClient,
    private route: Router,
    private loginService: LoginService
  ) { }

  
  /*
  private agregarAuthorizationHeader(){
    let token = this.loginService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer '+token);
    }
    else{
      return this.httpHeaders;
    }
  }

  private isNoAutorizado(e): boolean{

    if(e.status == 401){
      if(this.loginService.isAuthenticated()){
        this.loginService.logout();
      }
      this.route.navigate(['/login']);
      return true;
    }

    if(e.status == 403){
      swal('Acceso denergado', `Hola ${this.loginService.usuario.username} no tienes acceso a este recurso!`, 'warning');
      this.route.navigate(['/login']);
      return true;
    }
    return false;
    
  }*/

  getUsuarios(): Observable<Usuario[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Usuario[]>(this.urlEndPoint +'/empresa/empleado/' + usuario.idEmpresa);
  }

  create(usuario: Usuario): Observable<Usuario>{
    return this.http.post(this.urlEndPoint+'/empleado', usuario)
    .pipe(
      map((response: any) => response.usuario as Usuario),
      catchError(e => {

        if(e.status == 400){
          return throwError(e);
        }

        if(e.error.mensaje){
          console.error(e.error.mensaje);
        }
        return throwError(e);
      })
    );
  }

  edit(usuario: Usuario, id: number): Observable<Usuario>{
    return this.http.put<Usuario>(this.urlEndPoint+'/empleado/'+id, usuario)
    .pipe(
      map((response: any) => response.usuario as Usuario),
      catchError(e => {

        if(e.status == 400){
          return throwError(e);
        }

        if(e.error.mensaje){
          console.error(e.error.mensaje);
        }
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Usuario>{
    return this.http.delete<Usuario>(this.urlEndPoint+'/empleado/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getUsuarioById(id: number): Observable<Usuario>{
    let usuario = this.loginService.usuario;
    return this.http.get<Usuario>(this.urlEndPoint +'/empleado/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  addRol(rol: Rol, id: number): Observable<Usuario>{
    return this.http.put<Usuario>(this.urlEndPoint+'/rol/asignar/'+id, rol)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  deleteRol(id: number): Observable<Usuario>{
    return this.http.get<Usuario>(this.urlEndPoint+'/rol/eliminar/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

}
