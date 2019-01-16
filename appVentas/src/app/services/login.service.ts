import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { httpFactory } from '@angular/http/src/http_module';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    public http: HttpClient,
  ) { }

  login(usuario: Usuario):Observable<any>{
    const urlEndPoint = 'http://localhost:8090/oauth/token';

    const credenciales = btoa('angularapp' + ':' + '12345');

    const httpHeaders = new HttpHeaders({
      'Content-Type':'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + credenciales
    });

    let params = new URLSearchParams();
    params.set('grant_type','password');
    params.set('username',usuario.username);
    params.set('password',usuario.password);
    console.log(params.toString());
    return this.http.post(urlEndPoint, params.toString(), {headers: httpHeaders});
  }

}
