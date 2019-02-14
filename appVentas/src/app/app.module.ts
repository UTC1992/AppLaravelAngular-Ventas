import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HomeModule } from '../app/views/home/home.module';
import {
  SharedModule,
  LayoutComponent
} from './shared';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import { ModalcreateComponent } from './views/base/modalcreate/modalcreate.component';

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    ModalcreateComponent
    
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HomeModule,
    rootRouting,
    SharedModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.forRoot()
    
  ],
  providers: [
    HttpClient,
    BsModalRef
  ],
  bootstrap: [AppComponent],
  entryComponents: [ ModalcreateComponent ]
})
export class AppModule { }
