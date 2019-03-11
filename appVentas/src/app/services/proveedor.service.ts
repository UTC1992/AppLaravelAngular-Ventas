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

  getProveedores(): Observable<Proveedor[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Proveedor[]>(this.urlEndPoint +'/provedores/all/' + usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  create(proveedor: Proveedor): Observable<Proveedor>{
    return this.http.post(this.urlEndPoint+'/provedores', proveedor)
    .pipe(
      map((response: any) => response),
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

  delete(id: number): Observable<Proveedor>{
    return this.http.delete<Proveedor>(this.urlEndPoint+'/provedores/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getProvincias(): Observable<any[]>{
    return this.http.get<any[]>(this.urlEndPoint +'/provincias')
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getProveedorById(id: number): Observable<Proveedor>{
    return this.http.get<Proveedor>(this.urlEndPoint +'/provedores/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  edit(proveedor: Proveedor, id: number): Observable<Proveedor>{
    return this.http.put<Proveedor>(this.urlEndPoint+'/provedores/'+id, proveedor)
    .pipe(
      map((response: any) => response),
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

}
