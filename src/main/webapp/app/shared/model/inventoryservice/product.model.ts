import { Moment } from 'moment';

export interface IProduct {
  id?: number;
  createdDate?: Moment;
  productBuyingPrice?: number;
  productName?: string;
  producrSellingPrice?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public productBuyingPrice?: number,
    public productName?: string,
    public producrSellingPrice?: number
  ) {}
}
