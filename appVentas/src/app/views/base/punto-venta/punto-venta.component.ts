import { Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { PuntosVentaService } from '../../../services/puntos-venta.service';
import { LoginService } from '../../../services/login.service';

import { Usuario } from 'src/app/models/usuario';
import { PuntoVenta } from '../../../models/punto-venta';

import swal from 'sweetalert2';
import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';

import { from } from 'rxjs';

@Component({
  selector: 'app-punto-venta',
  templateUrl: './punto-venta.component.html',
  styleUrls: ['./punto-venta.component.scss']
})
export class PuntoVentaComponent implements OnInit {

  formCreate: FormGroup;
  formEdit: FormGroup;
  public sucursales: PuntoVenta[];

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

  mostrarPuntosVenta(){
    this.puntosService.getPustosVenta().subscribe(response => {
      console.log(response);
      this.sucursales = response;
    });
  }

  openModalCreate(template: TemplateRef<any>) {
    this.iniciarFormulario();
    this.tipoAccion = "create";
    this.titleModal = "Crear un punto de venta";
    this.modalRef = this.modalService.show(template);
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
  }

  cerrarModal(){
    this.modalRef.hide();
  }


}
