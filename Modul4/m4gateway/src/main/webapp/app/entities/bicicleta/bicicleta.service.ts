import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bicicleta } from './bicicleta.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bicicleta>;

@Injectable()
export class BicicletaService {

    private resourceUrl =  SERVER_API_URL + 'm4app/api/bicicletas';

    constructor(private http: HttpClient) { }

    create(bicicleta: Bicicleta): Observable<EntityResponseType> {
        const copy = this.convert(bicicleta);
        return this.http.post<Bicicleta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bicicleta: Bicicleta): Observable<EntityResponseType> {
        const copy = this.convert(bicicleta);
        return this.http.put<Bicicleta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bicicleta>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bicicleta[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bicicleta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bicicleta[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bicicleta = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bicicleta[]>): HttpResponse<Bicicleta[]> {
        const jsonResponse: Bicicleta[] = res.body;
        const body: Bicicleta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bicicleta.
     */
    private convertItemFromServer(bicicleta: Bicicleta): Bicicleta {
        const copy: Bicicleta = Object.assign({}, bicicleta);
        return copy;
    }

    /**
     * Convert a Bicicleta to a JSON which can be sent to the server.
     */
    private convert(bicicleta: Bicicleta): Bicicleta {
        const copy: Bicicleta = Object.assign({}, bicicleta);
        return copy;
    }
}
