// Angular
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { InicioComponent } from './inicio/inicio.component';

// Components Routing
import { BaseRoutingModule } from './base-routing.module';
import { UsuarioComponent } from './usuario/usuario.component';
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import { PuntoVentaComponent } from './punto-venta/punto-venta.component';
import { CategoriaProductoComponent } from './categoria-producto/categoria-producto.component';
import { TipoDocumentoComponent } from './tipo-documento/tipo-documento.component';
import { ProductoComponent } from './producto/producto.component';
import { ProveedorComponent } from './proveedor/proveedor.component';
import { ClienteComponent } from './cliente/cliente.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BaseRoutingModule,
    ReactiveFormsModule,
    ModalModule.forRoot()
  ],
  declarations: [
    InicioComponent,
    UsuarioComponent,
    PuntoVentaComponent,
    CategoriaProductoComponent,
    TipoDocumentoComponent,
    ProductoComponent,
    ProveedorComponent,
    ClienteComponent,
  ],
  
})
export class BaseModule { }
