import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderLine } from 'app/shared/model/OrderService/order-line.model';

@Component({
  selector: 'jhi-order-line-detail',
  templateUrl: './order-line-detail.component.html'
})
export class OrderLineDetailComponent implements OnInit {
  orderLine: IOrderLine;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLine }) => {
      this.orderLine = orderLine;
    });
  }

  previousState() {
    window.history.back();
  }
}
