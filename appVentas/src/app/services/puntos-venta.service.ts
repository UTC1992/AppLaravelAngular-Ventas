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

  /*private agregarAuthorizationHeader(){
    let token = this.loginService.token;
    if(token != null){
      return this.httpHeaders.append('Authorization', 'Bearer '+token);
    }
    else{
      return this.httpHeaders;
    }
  }*/

  getPustosVenta(): Observable<PuntoVenta[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<PuntoVenta[]>(this.urlEndPoint +'/punto/all/' + usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  create(punto: PuntoVenta): Observable<PuntoVenta>{
    return this.http.post(this.urlEndPoint+'/punto', punto)
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

  delete(id: number): Observable<PuntoVenta>{
    return this.http.delete<PuntoVenta>(this.urlEndPoint+'/punto/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getPuntoVentaById(id: number): Observable<PuntoVenta>{
    let usuario = this.loginService.usuario;
    return this.http.get<PuntoVenta>(this.urlEndPoint +'/punto/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  edit(punto: PuntoVenta, id: number): Observable<PuntoVenta>{
    return this.http.put<PuntoVenta>(this.urlEndPoint+'/punto/'+id, punto)
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
