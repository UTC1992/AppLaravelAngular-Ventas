export class Usuario {
    public id: number;
    public idEmpresa: number;
    public username: string;
    public password: string;
    public nombre: string;
    public apellido: string;
    public email: string;
    public dni: string;
    public enabled: boolean;
    public roles: string[] = [];
    public direccion: string;
    public cedula: string;
    public telefono: string;
    public celular: string;
}