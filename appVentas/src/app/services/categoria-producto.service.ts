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

  getCategorias(): Observable<CategoriaProducto[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<CategoriaProducto[]>(this.urlEndPoint +'/empresa/categorias/' + usuario.idEmpresa, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  create(punto: CategoriaProducto): Observable<CategoriaProducto>{
    return this.http.post(this.urlEndPoint+'/categorias', punto, {headers: this.agregarAuthorizationHeader()})
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

  delete(id: number): Observable<CategoriaProducto>{
    return this.http.delete<CategoriaProducto>(this.urlEndPoint+'/categorias/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  getPuntoVentaById(id: number): Observable<CategoriaProducto>{
    return this.http.get<CategoriaProducto>(this.urlEndPoint +'/categorias/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  edit(categoria: CategoriaProducto, id: number): Observable<CategoriaProducto>{
    return this.http.put<CategoriaProducto>(this.urlEndPoint+'/categorias/'+id, categoria, {headers: this.agregarAuthorizationHeader()})
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