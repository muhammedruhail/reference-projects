import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrderLine } from 'app/shared/model/inventoryservice/order-line.model';

type EntityResponseType = HttpResponse<IOrderLine>;
type EntityArrayResponseType = HttpResponse<IOrderLine[]>;

@Injectable({ providedIn: 'root' })
export class OrderLineService {
  public resourceUrl = SERVER_API_URL + 'api/order-lines';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/order-lines';

  constructor(private http: HttpClient) {}

  create(orderLine: IOrderLine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLine);
    return this.http
      .post<IOrderLine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderLine: IOrderLine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderLine);
    return this.http
      .put<IOrderLine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderLine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderLine[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderLine[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(orderLine: IOrderLine): IOrderLine {
    const copy: IOrderLine = Object.assign({}, orderLine, {
      orderDate: orderLine.orderDate != null && orderLine.orderDate.isValid() ? orderLine.orderDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderLine: IOrderLine) => {
        orderLine.orderDate = orderLine.orderDate != null ? moment(orderLine.orderDate) : null;
      });
    }
    return res;
  }
}
