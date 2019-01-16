import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { httpFactory } from '@angular/http/src/http_module';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private _usuario: Usuario;
  private _token: string;

  constructor(
      public http: HttpClient,
  ) {

  }

  public get usuario(): Usuario{
    if(this._usuario != null){
      return this._usuario;
    } else if(this._usuario == null && sessionStorage.getItem('usuario') != null){
      this._usuario = JSON.parse(sessionStorage.getItem('usuario')) as Usuario;
      return this._usuario;
    }
    return new Usuario();
  }

  public get token(): string{
    if(this._token != null){
      return this._token;
    } else if(this._token == null && sessionStorage.getItem('token') != null){
      this._token = sessionStorage.getItem('token');
      return this._token;
    }
    return null ;
  }

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

  guardarUsuario(accessToken: string): void{
    let datosToken = this.obtenerDatosToken(accessToken);
    this._usuario = new Usuario();
    this._usuario.nombre = datosToken.nombre_usuario;
    this._usuario.apellido = datosToken.apellido_usuario;
    this._usuario.email = datosToken.email;
    this._usuario.empresa_id = datosToken.empresa;
    this._usuario.username = datosToken.user_name;
    this._usuario.roles = datosToken.authorities;

    sessionStorage.setItem('usuario', JSON.stringify(this._usuario));
  }

  guardarToken(accessToken: string): void{
    this._token = accessToken;
    sessionStorage.setItem('token', accessToken);
  }

  obtenerDatosToken(accessToken: string): any{
    if(accessToken != null){
      return JSON.parse(atob(accessToken.split(".")[1]));
    } else {
      return null;
    }
  }

  isAuthenticated(): boolean{
    let datosToken = this.obtenerDatosToken(this.token);
    if(datosToken != null && datosToken.user_name && datosToken.user_name.length > 0){
      return true;
    }
    return false;
  }


}
