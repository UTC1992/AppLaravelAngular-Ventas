import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { PuntosVentaService } from '../../../services/puntos-venta.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { PuntoVenta } from '../../../models/punto-venta';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

import {MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-punto-venta',
  templateUrl: './punto-venta.component.html',
  styleUrls: ['./punto-venta.component.scss']
})
export class PuntoVentaComponent implements OnInit {

  displayedColumns: string[] = [
    'id', 'nombre', 'telefono', 'descripcion', 'direccion',  
    'provincia','ciudad', 'acciones'
  ];
  dataSource = new MatTableDataSource();

  formCreate: FormGroup;
  formEdit: FormGroup;
  public sucursales: PuntoVenta[];
  puntoEdit: PuntoVenta;

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  constructor(
    private puntosService: PuntosVentaService,
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
    this.subTitlePagina = "Sucursales";
    this.mostrarPuntosVenta();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      direccion: ['', Validators.required],
      provincia:['', Validators.required],
      ciudad:['', Validators.required],
      telefono:['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  iniciarFormularioEdit(){
    this.formCreate = this.formBuilder.group({
      nombre: [this.puntoEdit.nombre, Validators.required],
      descripcion: [this.puntoEdit.descripcion, Validators.required],
      direccion: [this.puntoEdit.direccion, Validators.required],
      provincia:[this.puntoEdit.provincia, Validators.required],
      ciudad:[this.puntoEdit.ciudad, Validators.required],
      telefono:[this.puntoEdit.telefono, Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarPuntosVenta(){
    this.puntosService.getPustosVenta().subscribe(response => {
      console.log(response);
      this.dataSource = new MatTableDataSource(response);
      //this.sucursales = response;
    }, error => {
      this.sucursales = [];
      console.log(error.error.mensaje);
    });
  }

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear un punto de venta";
    this.modalRef = this.modalService.show(template);
  }

  openModalEdit(template: TemplateRef<any>, id) {
    this.titleModal = "Editar datos del punto de venta";
    this.tipoAccion = "edit";
    this.puntosService.getPuntoVentaById(id).subscribe(response =>{
      if(response != null){
        this.puntoEdit = response;
        this.iniciarFormularioEdit();
        this.modalRef = this.modalService.show(template);
      }
      
    });
    
  }

  enviarDatos(){
    console.log(this.formCreate.value);
    console.log("Tipo de accion ==> "+this.tipoAccion);
    if(this.tipoAccion == "create"){
      this.puntosService.create(this.formCreate.value).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Punto de venta guardado');
          this.mostrarPuntosVenta();
          this.cerrarModal();
          swal('Éxito', 'Punto de venta creado con exito!', 'success');
        } else {
          console.log('Punto de venta error al guardar');
        }
      });
    }
    if(this.tipoAccion == "edit"){
      this.puntosService.edit(this.formCreate.value, this.puntoEdit.id).subscribe(response => {
        console.log(response);
        if(response){
          console.log('Punto de venta actualizado');
          this.mostrarPuntosVenta();
          this.cerrarModal();
          swal('Éxito', 'Punto de venta modificado con exito!', 'success');
        } else {
          console.log('Punto de venta error al modificar');
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
      text: "Se eliminará el punto de venta",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.value) {
        this.eliminarPuntoVenta(id);
      }
    });
  }

  eliminarPuntoVenta(id){
    this.puntosService.delete(id).subscribe(response => {
      console.log(response);
      if(response){
        swal(
          'Eliminado!',
          'El Punto de Venta a sido eliminado.',
          'success'
        )
        this.mostrarPuntosVenta();
      }
    });
  }

}
