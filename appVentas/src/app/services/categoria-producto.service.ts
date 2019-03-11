import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Usuario } from '../models/usuario';
import { CategoriaProducto } from '../models/categoria-producto';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CategoriaProductoService {

  private urlEndPoint: string = 'http://localhost:8090/api';

  private httpHeaders = new HttpHeaders({
    'Content-Type':'application/json'
  });

  constructor(
    private http: HttpClient,
    private route: Router,
    private loginService: LoginService
  ) { 

  }

  getCategorias(): Observable<CategoriaProducto[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<CategoriaProducto[]>(this.urlEndPoint +'/empresa/categorias/' + usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  create(punto: CategoriaProducto): Observable<CategoriaProducto>{
    return this.http.post(this.urlEndPoint+'/categorias', punto)
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

  delete(id: number): Observable<CategoriaProducto>{
    return this.http.delete<CategoriaProducto>(this.urlEndPoint+'/categorias/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getPuntoVentaById(id: number): Observable<CategoriaProducto>{
    return this.http.get<CategoriaProducto>(this.urlEndPoint +'/categorias/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  edit(categoria: CategoriaProducto, id: number): Observable<CategoriaProducto>{
    return this.http.put<CategoriaProducto>(this.urlEndPoint+'/categorias/'+id, categoria)
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
