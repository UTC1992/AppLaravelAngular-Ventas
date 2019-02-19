import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { PuntoVenta } from '../models/punto-venta';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class PuntosVentaService {

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

  getPustosVenta(): Observable<PuntoVenta[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<PuntoVenta[]>(this.urlEndPoint +'/punto/all/' + usuario.idEmpresa, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  create(punto: PuntoVenta): Observable<PuntoVenta>{
    return this.http.post(this.urlEndPoint+'/punto', punto, {headers: this.agregarAuthorizationHeader()})
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

  delete(id: number): Observable<PuntoVenta>{
    return this.http.delete<PuntoVenta>(this.urlEndPoint+'/punto/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  getPuntoVentaById(id: number): Observable<PuntoVenta>{
    let usuario = this.loginService.usuario;
    return this.http.get<PuntoVenta>(this.urlEndPoint +'/punto/'+id, {headers: this.agregarAuthorizationHeader()})
    .pipe(catchError( e => {
      this.isNoAutorizado(e);
      return throwError(e);
    })
    );
  }

  edit(punto: PuntoVenta, id: number): Observable<PuntoVenta>{
    return this.http.put<PuntoVenta>(this.urlEndPoint+'/punto/'+id, punto, {headers: this.agregarAuthorizationHeader()})
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
