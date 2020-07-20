import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrderLine } from 'app/shared/model/inventoryservice/order-line.model';
import { OrderLineService } from './order-line.service';

@Component({
  selector: 'jhi-order-line-update',
  templateUrl: './order-line-update.component.html'
})
export class OrderLineUpdateComponent implements OnInit {
  orderLine: IOrderLine;
  isSaving: boolean;
  orderDate: string;

  constructor(private orderLineService: OrderLineService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orderLine }) => {
      this.orderLine = orderLine;
      this.orderDate = this.orderLine.orderDate != null ? this.orderLine.orderDate.format(DATE_TIME_FORMAT) : null;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.orderLine.orderDate = this.orderDate != null ? moment(this.orderDate, DATE_TIME_FORMAT) : null;
    if (this.orderLine.id !== undefined) {
      this.subscribeToSaveResponse(this.orderLineService.update(this.orderLine));
    } else {
      this.subscribeToSaveResponse(this.orderLineService.create(this.orderLine));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderLine>>) {
    result.subscribe((res: HttpResponse<IOrderLine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
