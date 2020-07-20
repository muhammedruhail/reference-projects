import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInvoice } from 'app/shared/model/inventoryservice/invoice.model';
import { InvoiceService } from './invoice.service';
import { IOrderLine } from 'app/shared/model/inventoryservice/order-line.model';
import { OrderLineService } from 'app/entities/inventoryservice/order-line';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html'
})
export class InvoiceUpdateComponent implements OnInit {
  invoice: IInvoice;
  isSaving: boolean;

  orderlines: IOrderLine[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private invoiceService: InvoiceService,
    private orderLineService: OrderLineService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.invoice = invoice;
    });
    this.orderLineService.query({ filter: 'invoice-is-null' }).subscribe(
      (res: HttpResponse<IOrderLine[]>) => {
        if (!this.invoice.orderLineId) {
          this.orderlines = res.body;
        } else {
          this.orderLineService.find(this.invoice.orderLineId).subscribe(
            (subRes: HttpResponse<IOrderLine>) => {
              this.orderlines = [subRes.body].concat(res.body);
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
    if (this.invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(this.invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(this.invoice));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>) {
    result.subscribe((res: HttpResponse<IInvoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOrderLineById(index: number, item: IOrderLine) {
    return item.id;
  }
}
