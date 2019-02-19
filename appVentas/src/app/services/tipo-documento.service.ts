import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { TipoDocumento } from '../models/tipo-documento';

import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class TipoDocumentoService {

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

  getTipoDocumentos(): Observable<TipoDocumento[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<TipoDocumento[]>(this.urlEndPoint +'/empresa/tipo/' + usuario.idEmpresa, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  create(documento: TipoDocumento): Observable<TipoDocumento>{
    return this.http.post(this.urlEndPoint+'/tipo', documento, {headers: this.agregarAuthorizationHeader()})
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

  delete(id: number): Observable<TipoDocumento>{
    return this.http.delete<TipoDocumento>(this.urlEndPoint+'/tipo/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  getTipoDocumentoById(id: number): Observable<TipoDocumento>{
    return this.http.get<TipoDocumento>(this.urlEndPoint +'/tipo/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  edit(documento: TipoDocumento, id: number): Observable<TipoDocumento>{
    return this.http.put<TipoDocumento>(this.urlEndPoint+'/tipo/'+id, documento, {headers: this.agregarAuthorizationHeader()})
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
