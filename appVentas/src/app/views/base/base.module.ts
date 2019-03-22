// Angular
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule, NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

// Components Routing
import { InicioComponent } from './inicio/inicio.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { PuntoVentaComponent } from './punto-venta/punto-venta.component';
import { CategoriaProductoComponent } from './categoria-producto/categoria-producto.component';
import { TipoDocumentoComponent } from './tipo-documento/tipo-documento.component';
import { ProductoComponent } from './producto/producto.component';
import { ProveedorComponent } from './proveedor/proveedor.component';
import { ClienteComponent } from './cliente/cliente.component';
import { VentaComponent } from './venta/venta.component';

//MODULOS
import { BaseRoutingModule } from './base-routing.module';
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';

//MATERIAL DESIGNER
import { MatTableModule, MatToolbarModule, 
  MatButtonModule, MatSidenavModule, MatIconModule, 
  MatListModule, MatGridListModule, MatCardModule, 
  MatMenuModule, MatDatepickerModule, MatNativeDateModule, 
  MatRadioModule, MatChipsModule, MatSelectModule, MatTabsModule, MatDialogModule } from '@angular/material';
  import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatExpansionModule} from '@angular/material/expansion';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BaseRoutingModule,
    ReactiveFormsModule,
    ModalModule.forRoot(),
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatTableModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatChipsModule,
    MatSelectModule,
    MatTabsModule,
    MatDialogModule,
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
    VentaComponent,
  ],
  providers: [
  ],
  exports: [
    
  ],
  schemas: [
    NO_ERRORS_SCHEMA, 
    CUSTOM_ELEMENTS_SCHEMA
  ],
  entryComponents: [
  ],
  
})
export class BaseModule { }
