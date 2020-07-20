/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InventoryserviceTestModule } from '../../../../test.module';
import { OrderLineDetailComponent } from 'app/entities/inventoryservice/order-line/order-line-detail.component';
import { OrderLine } from 'app/shared/model/inventoryservice/order-line.model';

describe('Component Tests', () => {
  describe('OrderLine Management Detail Component', () => {
    let comp: OrderLineDetailComponent;
    let fixture: ComponentFixture<OrderLineDetailComponent>;
    const route = ({ data: of({ orderLine: new OrderLine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InventoryserviceTestModule],
        declarations: [OrderLineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrderLineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderLineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderLine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
