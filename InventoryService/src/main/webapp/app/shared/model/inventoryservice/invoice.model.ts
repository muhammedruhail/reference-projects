export interface IInvoice {
  id?: number;
  customerName?: string;
  grossTotal?: number;
  orderLineId?: number;
}

export class Invoice implements IInvoice {
  constructor(public id?: number, public customerName?: string, public grossTotal?: number, public orderLineId?: number) {}
}
