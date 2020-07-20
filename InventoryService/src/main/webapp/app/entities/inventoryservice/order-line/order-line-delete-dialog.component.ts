import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLine } from 'app/shared/model/inventoryservice/order-line.model';
import { OrderLineService } from './order-line.service';

@Component({
  selector: 'jhi-order-line-delete-dialog',
  templateUrl: './order-line-delete-dialog.component.html'
})
export class OrderLineDeleteDialogComponent {
  orderLine: IOrderLine;

  constructor(private orderLineService: OrderLineService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderLineService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderLineListModification',
        content: 'Deleted an orderLine'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-line-delete-popup',
  template: ''
})
export class OrderLineDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLine }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderLineDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderLine = orderLine;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
