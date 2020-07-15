import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Stock } from 'app/shared/model/inventoryservice/stock.model';
import { StockService } from './stock.service';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';
import { StockDeletePopupComponent } from './stock-delete-dialog.component';
import { IStock } from 'app/shared/model/inventoryservice/stock.model';

@Injectable({ providedIn: 'root' })
export class StockResolve implements Resolve<IStock> {
  constructor(private service: StockService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Stock> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Stock>) => response.ok),
        map((stock: HttpResponse<Stock>) => stock.body)
      );
    }
    return of(new Stock());
  }
}

export const stockRoute: Routes = [
  {
    path: 'stock',
    component: StockComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'inventoryserviceApp.inventoryserviceStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'stock/:id/view',
    component: StockDetailComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'inventoryserviceApp.inventoryserviceStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'stock/new',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'inventoryserviceApp.inventoryserviceStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'stock/:id/edit',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'inventoryserviceApp.inventoryserviceStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stockPopupRoute: Routes = [
  {
    path: 'stock/:id/delete',
    component: StockDeletePopupComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'inventoryserviceApp.inventoryserviceStock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
