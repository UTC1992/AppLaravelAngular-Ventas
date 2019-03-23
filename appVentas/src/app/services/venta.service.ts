import { Injectable } from '@angular/core';
import { Observable, from, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Usuario } from 'src/app/models/usuario';
import { Venta } from '../models/venta';

import { Router } from '@angular/router';
import { LoginService } from './login.service';

import { map, filter, catchError, mergeMap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class VentaService {

  private urlEndPoint: string = 'http://localhost:8090/api';

  constructor(
    private http: HttpClient,
    private route: Router,
    private loginService: LoginService
  ) { }

  getVentas(): Observable<Venta[]>{
    let usuario = this.loginService.usuario;
    return this.http.get<Venta[]>(this.urlEndPoint +'/ventas/empresa/' + usuario.idEmpresa)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  getVentaById(id: number): Observable<Venta>{
    return this.http.get<Venta>(this.urlEndPoint +'/ventas/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

  create(venta: Venta): Observable<Venta>{
    return this.http.post(this.urlEndPoint+'/ventas', venta)
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

  delete(id: number): Observable<Venta>{
    return this.http.delete<Venta>(this.urlEndPoint+'/ventas/'+id)
    .pipe(catchError( e => {
      if(e.error.mensaje){
        console.error(e.error.mensaje);
      }
      return throwError(e);
    })
    );
  }

}
