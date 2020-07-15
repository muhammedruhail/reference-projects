import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStock } from 'app/shared/model/inventoryservice/stock.model';
import { StockService } from './stock.service';
import { IProduct } from 'app/shared/model/inventoryservice/product.model';
import { ProductService } from 'app/entities/inventoryservice/product';
import { ICategory } from 'app/shared/model/inventoryservice/category.model';
import { CategoryService } from 'app/entities/inventoryservice/category';

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html'
})
export class StockUpdateComponent implements OnInit {
  stock: IStock;
  isSaving: boolean;

  products: IProduct[];

  categories: ICategory[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private stockService: StockService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.stock = stock;
    });
    this.productService.query({ filter: 'stock-is-null' }).subscribe(
      (res: HttpResponse<IProduct[]>) => {
        if (!this.stock.productId) {
          this.products = res.body;
        } else {
          this.productService.find(this.stock.productId).subscribe(
            (subRes: HttpResponse<IProduct>) => {
              this.products = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.categoryService.query({ filter: 'stock-is-null' }).subscribe(
      (res: HttpResponse<ICategory[]>) => {
        if (!this.stock.categoryId) {
          this.categories = res.body;
        } else {
          this.categoryService.find(this.stock.categoryId).subscribe(
            (subRes: HttpResponse<ICategory>) => {
              this.categories = [subRes.body].concat(res.body);
            },
            (subRes: HttpErrorResponse) => this.onError(subRes.message)
          );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.stock.id !== undefined) {
      this.subscribeToSaveResponse(this.stockService.update(this.stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.create(this.stock));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>) {
    result.subscribe((res: HttpResponse<IStock>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }
}
