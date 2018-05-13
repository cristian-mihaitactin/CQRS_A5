import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bike } from './bike.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bike>;

@Injectable()
export class BikeService {

    private resourceUrl =  SERVER_API_URL + 'bike/api/bikes';

    constructor(private http: HttpClient) { }

    create(bike: Bike): Observable<EntityResponseType> {
        const copy = this.convert(bike);
        return this.http.post<Bike>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bike: Bike): Observable<EntityResponseType> {
        const copy = this.convert(bike);
        return this.http.put<Bike>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bike>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bike[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bike[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bike[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bike = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bike[]>): HttpResponse<Bike[]> {
        const jsonResponse: Bike[] = res.body;
        const body: Bike[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bike.
     */
    private convertItemFromServer(bike: Bike): Bike {
        const copy: Bike = Object.assign({}, bike);
        return copy;
    }

    /**
     * Convert a Bike to a JSON which can be sent to the server.
     */
    private convert(bike: Bike): Bike {
        const copy: Bike = Object.assign({}, bike);
        return copy;
    }
}
