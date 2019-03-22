import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { ProveedorService } from '../../../services/proveedor.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { Proveedor } from '../../../models/proveedor';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-proveedor',
  templateUrl: './proveedor.component.html',
  styleUrls: ['./proveedor.component.scss']
})
export class ProveedorComponent implements OnInit {

  displayedColumns: string[] = [
    'id', 'ruc','cedula', 'nombre',  
    'telefono', 'celular', 'email', 
    'provincia', 
    'ciudad', 'direccion1', 'cuenta1', 'acciones'
  ];
  dataSource = new MatTableDataSource();

  formCreate: FormGroup;
  formEdit: FormGroup;
  public proveedores: Proveedor[];
  proveedorEdit: Proveedor;

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  provincias: any[];

  constructor(
    private proveedorServices: ProveedorService,
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
    this.subTitlePagina = "Proveedores";
    this.mostrarProveedores();
    this.obtenerProvincias();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      ruc: ['', Validators.required],
      cedula:['', Validators.required],
      direccion1:['', Validators.required],
      direccion2:['', ],
      telefono:['', Validators.required],
      provincia:['', Validators.required],
      ciudad:['', Validators.required],
      sitioWeb:['', ],
      celular:['', Validators.required],
      email:['', Validators.required],
      cuenta1:['', Validators.required],
      cuenta2:['',],
      estado:['', Validators.requiredTrue],
      observacion:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      nombre: [this.proveedorEdit.nombre, Validators.required],
      apellido: [this.proveedorEdit.apellido, Validators.required],
      ruc: [this.proveedorEdit.ruc, Validators.required],
      cedula:[this.proveedorEdit.cedula, Validators.required],
      direccion1:[this.proveedorEdit.direccion1, Validators.required],
      direccion2:[this.proveedorEdit.direccion2, ],
      telefono:[this.proveedorEdit.telefono, Validators.required],
      provincia:[this.proveedorEdit.provincia, Validators.required],
      ciudad:[this.proveedorEdit.ciudad, Validators.required],
      sitioWeb:[this.proveedorEdit.sitioWeb, ],
      celular:[this.proveedorEdit.cedula, Validators.required],
      email:[this.proveedorEdit.email, Validators.required],
      cuenta1:[this.proveedorEdit.cuenta1, Validators.required],
      cuenta2:[this.proveedorEdit.cuenta2,],
      estado:[this.proveedorEdit.estado, Validators.requiredTrue],
      observacion:[this.proveedorEdit.observacion, Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarProveedores(){
    this.proveedorServices.getProveedores().subscribe(response => {
      console.log(response);
      this.dataSource = new MatTableDataSource(response);
      //this.proveedores = response;
    }, error => {
      this.proveedores = [];
      console.log(error.error.mensaje);
    });
  }

  obtenerProvincias(){
    this.proveedorServices.getProvincias().subscribe(response => {
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
    this.titleModal = "Crear un nuevo proveedor";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del proveedor";
    this.tipoAccion = "edit";
    this.proveedorServices.getProveedorById(id).subscribe(response =>{
      if(response != null){
        this.proveedorEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
      
    });
    
  }

  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.proveedorServices.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Proveedor guardado');
          this.mostrarProveedores();
          this.cerrarModal();
          swal('Éxito', 'Proveedor creado con exito!', 'success');
        } else {
          console.log('Proveedor error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.proveedorServices.edit(this.formCreate.value, this.proveedorEdit.id).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Proveedor modificado');
          this.mostrarProveedores();
          this.cerrarModal();
          swal('Éxito', 'Proveedor modificado con exito!', 'success');
        } else {
          console.log('Proveedor error al modificar');
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
      text: "Se eliminará el proveedor",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarProveedor(id);
      }
    });
  }

  eliminarProveedor(id){
    this.proveedorServices.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'El Proveedor a sido eliminado.',
          'success'
        )
        this.mostrarProveedores();
      }
    });
  }

}
