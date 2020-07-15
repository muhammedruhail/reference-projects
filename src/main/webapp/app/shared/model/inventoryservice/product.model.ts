import { Moment } from 'moment';

export interface IProduct {
  id?: number;
  createdUser?: string;
  lastModificationDate?: Moment;
  lastModifiedUser?: string;
  productBuyingPrice?: number;
  productName?: string;
  producrSellingPrice?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public createdUser?: string,
    public lastModificationDate?: Moment,
    public lastModifiedUser?: string,
    public productBuyingPrice?: number,
    public productName?: string,
    public producrSellingPrice?: number
  ) {}
}
