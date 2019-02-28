import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Usuario } from 'src/app/models/usuario';
import { Proveedor } from '../models/proveedor';

import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import swal from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class ProveedorService {

  private urlEndPoint: string = 'http://localhost:8090/api';

  private httpHeaders = new HttpHeaders({
    'Content-Type':'application/json'
  });

  constructor(
    private http: HttpClient,
    private route: Router,
    private loginService: LoginService
  ) { }

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
    if(e.status == 401 || e.status == 403){
      this.route.navigate(['/login']);
      return true;
    } else {
      return false;
    }
  }

  getProveedores(): Observable<Proveedor[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Proveedor[]>(this.urlEndPoint +'/provedores/all/' + usuario.idEmpresa, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  create(proveedor: Proveedor): Observable<Proveedor>{
    return this.http.post(this.urlEndPoint+'/provedores', proveedor, {headers: this.agregarAuthorizationHeader()})
    .pipe(
      map((response: any) => response),
      catchError(e => {
        if(this.isNoAutorizado(e)){
          return throwError(e);
        }

        if(e.status == 400){
          return throwError(e);
        }

        console.error(e.error.mensaje);
        swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Proveedor>{
    return this.http.delete<Proveedor>(this.urlEndPoint+'/provedores/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  getProvincias(): Observable<any[]>{
    return this.http.get<any[]>(this.urlEndPoint +'/provincias', {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  getProveedorById(id: number): Observable<Proveedor>{
    return this.http.get<Proveedor>(this.urlEndPoint +'/provedores/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  edit(proveedor: Proveedor, id: number): Observable<Proveedor>{
    return this.http.put<Proveedor>(this.urlEndPoint+'/provedores/'+id, proveedor, {headers: this.agregarAuthorizationHeader()})
    .pipe(
      map((response: any) => response),
      catchError(e => {
        if(this.isNoAutorizado(e)){
          return throwError(e);
        }

        if(e.status == 400){
          return throwError(e);
        }

        console.error(e.error.mensaje);
        swal(e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

}
