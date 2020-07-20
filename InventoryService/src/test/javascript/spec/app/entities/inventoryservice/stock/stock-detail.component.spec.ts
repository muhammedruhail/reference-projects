/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InventoryserviceTestModule } from '../../../../test.module';
import { StockDetailComponent } from 'app/entities/inventoryservice/stock/stock-detail.component';
import { Stock } from 'app/shared/model/inventoryservice/stock.model';

describe('Component Tests', () => {
  describe('Stock Management Detail Component', () => {
    let comp: StockDetailComponent;
    let fixture: ComponentFixture<StockDetailComponent>;
    const route = ({ data: of({ stock: new Stock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InventoryserviceTestModule],
        declarations: [StockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
