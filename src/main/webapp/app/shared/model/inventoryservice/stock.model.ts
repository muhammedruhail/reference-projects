export interface IStock {
  id?: number;
  stockBuyingPrice?: number;
  grossProfit?: number;
  units?: number;
  productId?: number;
  categoryId?: number;
}

export class Stock implements IStock {
  constructor(
    public id?: number,
    public stockBuyingPrice?: number,
    public grossProfit?: number,
    public units?: number,
    public productId?: number,
    public categoryId?: number
  ) {}
}
