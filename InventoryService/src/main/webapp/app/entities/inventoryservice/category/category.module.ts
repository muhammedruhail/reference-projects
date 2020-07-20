import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InventoryserviceSharedModule } from 'app/shared';
import {
  CategoryComponent,
  CategoryDetailComponent,
  CategoryUpdateComponent,
  CategoryDeletePopupComponent,
  CategoryDeleteDialogComponent,
  categoryRoute,
  categoryPopupRoute
} from './';

const ENTITY_STATES = [...categoryRoute, ...categoryPopupRoute];

@NgModule({
  imports: [InventoryserviceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategoryComponent,
    CategoryDetailComponent,
    CategoryUpdateComponent,
    CategoryDeleteDialogComponent,
    CategoryDeletePopupComponent
  ],
  entryComponents: [CategoryComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent, CategoryDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InventoryserviceCategoryModule {}