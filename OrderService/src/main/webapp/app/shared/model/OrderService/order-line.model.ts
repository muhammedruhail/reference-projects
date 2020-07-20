import { Moment } from 'moment';

export interface IOrderLine {
  id?: number;
  orderDate?: Moment;
  orderStatus?: boolean;
  productId?: number;
  customerId?: number;
}

export class OrderLine implements IOrderLine {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public orderStatus?: boolean,
    public productId?: number,
    public customerId?: number
  ) {
    this.orderStatus = this.orderStatus || false;
  }
}
