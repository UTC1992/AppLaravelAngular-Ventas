import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { ClienteService } from '../../../services/cliente.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { Cliente } from '../../../models/cliente';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.scss']
})
export class ClienteComponent implements OnInit {

  formCreate: FormGroup;
  formEdit: FormGroup;
  public clientes: Cliente[];
  clientesEdit: Cliente;

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  provincias: any[];

  constructor(
    private clienteService: ClienteService,
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private modalService: BsModalService,
    public modalRef: BsModalRef,
  ) { 
    this.iniciarFormulario();
    this.usuario = this.loginService.usuario;
    this.idEmpresa = this.usuario.idEmpresa;
  }

  ngOnInit() {
    this.subTitlePagina = "Clientes";
    this.mostrarClientes();
    this.obtenerProvincias();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombres: ['', Validators.required],
      apellidos: ['', Validators.required],
      ruc: ['', Validators.required],
      cedula:['', Validators.required],
      direccion:['', Validators.required],
      telefono:['', Validators.required],
      provincia:['', Validators.required],
      ciudad:['', Validators.required],
      sitioWeb:['', ],
      email:['', Validators.required],
      observaciones:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      nombres: [this.clientesEdit.nombres, Validators.required],
      apellidos: [this.clientesEdit.apellidos, Validators.required],
      ruc: [this.clientesEdit.ruc, Validators.required],
      cedula:[this.clientesEdit.cedula, Validators.required],
      direccion:[this.clientesEdit.direccion, Validators.required],
      telefono:[this.clientesEdit.telefono, Validators.required],
      provincia:[this.clientesEdit.provincia, Validators.required],
      ciudad:[this.clientesEdit.ciudad, Validators.required],
      sitioWeb:[this.clientesEdit.sitioWeb, ],
      email:[this.clientesEdit.email, Validators.required],
      observaciones:[this.clientesEdit.observaciones, Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarClientes(){
    this.clienteService.getClientes().subscribe(response => {
      console.log(response);
      this.clientes = response;
    }, error => {
      this.clientes = [];
      console.log(error.error.mensaje);
    });
  }

  obtenerProvincias(){
    this.clienteService.getProvincias().subscribe(response => {
      console.log(response);
      this.provincias = response;
    }, error => {
      this.provincias = [];
      console.log(error.error.mensaje);
    });
  }

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear un nuevo cliente";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del proveedor";
    this.tipoAccion = "edit";
    this.clienteService.getClienteById(id).subscribe(response =>{
      if(response != null){
        this.clientesEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
    });
  }

  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.clienteService.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Cliente guardado');
          this.mostrarClientes();
          this.cerrarModal();
          swal('Éxito', 'Cliente creado con exito!', 'success');
        } else {
          console.log('Cliente error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.clienteService.edit(this.formCreate.value, this.clientesEdit.idCliente).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Cliente modificado');
          this.mostrarClientes();
          this.cerrarModal();
          swal('Éxito', 'Cliente modificado con exito!', 'success');
        } else {
          console.log('Cliente error al modificar');
        }
      });
    }
  }

  cerrarModal(){
    this.modalRef.hide();
  }

  confirmarEliminar(id){
    swal({
      title: '¿Está seguro?',
      text: "Se eliminará el cliente",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarCliente(id);
      }
    });
  }

  eliminarCliente(id){
    this.clienteService.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'El Cliente a sido eliminado.',
          'success'
        )
        this.mostrarClientes();
      }
    });
  }

}
