import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { InicioComponent } from './inicio/inicio.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { PuntoVentaComponent } from './punto-venta/punto-venta.component';
import { CategoriaProductoComponent } from './categoria-producto/categoria-producto.component';
import { TipoDocumentoComponent } from './tipo-documento/tipo-documento.component';
import { ProductoComponent } from './producto/producto.component';
import { ProveedorComponent } from './proveedor/proveedor.component';
import { ClienteComponent } from './cliente/cliente.component';

import { LoginGuard } from '../../guards/login.guard';
import { RoleGuard } from '../../guards/role.guard';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Base'
    },
    children: [
      {
        path: '',
        redirectTo: 'inicio'
      },
      {
        path: 'inicio',
        component: InicioComponent,
        canActivate: [LoginGuard],
        data: {
          title: 'Inicio'
        }
      },
      {
        path: 'usuario',
        component: UsuarioComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Usuarios',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'puntos-venta',
        component: PuntoVentaComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Puntos de Venta',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'categoria-producto',
        component: CategoriaProductoComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Categoría de productos',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'tipo-documento',
        component: TipoDocumentoComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Tipo de documentos',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'producto',
        component: ProductoComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Productos',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'proveedor',
        component: ProveedorComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Proveedores',
          role: 'ROLE_ADMIN'
        }
      },
      {
        path: 'cliente',
        component: ClienteComponent,
        canActivate: [LoginGuard, RoleGuard],
        data: {
          title: 'Clientes',
          role: 'ROLE_ADMIN'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
  ]
})
export class BaseRoutingModule {}
