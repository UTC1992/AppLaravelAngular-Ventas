import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Usuario } from 'src/app/models/usuario';
import { Producto } from '../models/producto';
import { TipoProducto } from '../models/tipo-producto';
import { CategoriaProducto } from '../models/categoria-producto';

import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private urlEndPoint: string = 'http://localhost:8090/api';

  private httpHeaders = new HttpHeaders({
    'Content-Type':'application/json'
  });

  constructor(
    private http: HttpClient,
    private route: Router,
    private loginService: LoginService
  ) { }

  getProductos(): Observable<Producto[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Producto[]>(this.urlEndPoint +'/productos/all/' + usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  create(producto: Producto): Observable<Producto>{
    return this.http.post(this.urlEndPoint+'/productos', producto)
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

  delete(id: number): Observable<Producto>{
    return this.http.delete<Producto>(this.urlEndPoint+'/productos/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getProductoById(id: number): Observable<Producto>{
    return this.http.get<Producto>(this.urlEndPoint +'/productos/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  edit(producto: Producto, id: number): Observable<Producto>{
    return this.http.put<Producto>(this.urlEndPoint+'/productos/'+id, producto)
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

  getTipoProductos(): Observable<TipoProducto[]>{
    return this.http.get<TipoProducto[]>(this.urlEndPoint +'/tipo_producto')
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  filtrarProductos(texto: string): Observable<Producto[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Producto[]>(this.urlEndPoint +'/productos/codigo/' + texto + '/'+ usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }


}
