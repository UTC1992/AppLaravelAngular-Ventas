import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HomeModule } from '../app/views/home/home.module';
import {
  FooterComponent,
  HeaderComponent,
  SharedModule,
  LayoutComponent
} from './shared';

import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });


@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    LayoutComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HomeModule,
    rootRouting,
    SharedModule,
    HttpClientModule,
    
  ],
  providers: [
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
