import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProduct } from 'app/shared/model/inventoryservice/product.model';
import { ProductService } from './product.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  product: IProduct;
  isSaving: boolean;
  lastModificationDate: string;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      this.lastModificationDate =
        this.product.lastModificationDate != null ? this.product.lastModificationDate.format(DATE_TIME_FORMAT) : null;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.product.lastModificationDate = this.lastModificationDate != null ? moment(this.lastModificationDate, DATE_TIME_FORMAT) : null;
    if (this.product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(this.product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(this.product));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
