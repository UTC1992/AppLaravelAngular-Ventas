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

import { HttpClient, HttpHeaders, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });
import { ModalModule, BsModalRef } from 'ngx-bootstrap/modal';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { LoginInterceptor } from './interceptors/login.interceptor';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    
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
    ModalModule.forRoot(),
    MatAutocompleteModule,
    MatInputModule,
    MatFormFieldModule,
    BrowserAnimationsModule
    
  ],
  providers: [
    HttpClient,
    BsModalRef,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoginInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
