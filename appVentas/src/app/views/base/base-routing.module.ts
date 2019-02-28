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
        data: {
          title: 'Inicio'
        }
      },
      {
        path: 'usuario',
        component: UsuarioComponent,
        data: {
          title: 'Usuarios'
        }
      },
      {
        path: 'puntos-venta',
        component: PuntoVentaComponent,
        data: {
          title: 'Puntos de Venta'
        }
      },
      {
        path: 'categoria-producto',
        component: CategoriaProductoComponent,
        data: {
          title: 'Categoría de productos'
        }
      },
      {
        path: 'tipo-documento',
        component: TipoDocumentoComponent,
        data: {
          title: 'Tipo de documentos'
        }
      },
      {
        path: 'producto',
        component: ProductoComponent,
        data: {
          title: 'Productos'
        }
      },
      {
        path: 'proveedor',
        component: ProveedorComponent,
        data: {
          title: 'Proveedores'
        }
      },
      {
        path: 'cliente',
        component: ClienteComponent,
        data: {
          title: 'Clientes'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BaseRoutingModule {}
