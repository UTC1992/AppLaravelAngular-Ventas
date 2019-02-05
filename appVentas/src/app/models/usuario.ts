export class Usuario {
    public empresa_id: number;
    public username: string;
    public password: string;
    public nombre: string;
    public apellido: string;
    public email: string;
    public dni: string;
    public enabled: boolean;
    public roles: string[] = [];
}