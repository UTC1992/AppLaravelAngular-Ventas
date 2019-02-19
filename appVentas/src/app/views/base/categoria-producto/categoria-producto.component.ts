import { Component, OnInit, TemplateRef  } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

import { LoginService } from '../../../services/login.service';
import { CategoriaProductoService } from '../../../services/categoria-producto.service';

import { Usuario } from 'src/app/models/usuario';
import { CategoriaProducto } from '../../../models/categoria-producto';

import {BsModalRef, BsModalService,  } from 'ngx-bootstrap/modal';
import swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-producto',
  templateUrl: './categoria-producto.component.html',
  styleUrls: ['./categoria-producto.component.scss']
})
export class CategoriaProductoComponent implements OnInit {

  formCreate: FormGroup;
  formEdit: FormGroup;
  public categorias: CategoriaProducto[];

  idEmpresa: any;
  usuario: Usuario;

  tipoAccion: string;
  titleModal: string;

  subTitlePagina: string;

  constructor(
    private categoriaService: CategoriaProductoService,
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
    this.subTitlePagina = "Categorías para los productos";
    this.mostrarCategorias();
  }

  iniciarFormulario(){
    this.formCreate = this.formBuilder.group({
      nombreCategoria: ['', Validators.required],
      descripcionCategoria: ['', Validators.required],
      idEmpresa:[this.idEmpresa, Validators.required]
    });
  }

  mostrarCategorias(){
    this.categoriaService.getCategorias().subscribe(response => {
      console.log(response);
      this.categorias = response;
    });
  }  

}
