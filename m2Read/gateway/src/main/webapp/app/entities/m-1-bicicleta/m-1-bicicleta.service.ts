import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { M1bicicleta } from './m-1-bicicleta.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<M1bicicleta>;

@Injectable()
export class M1bicicletaService {

    private resourceUrl =  SERVER_API_URL + 'm1app/api/m-1-bicicletas';

    constructor(private http: HttpClient) { }

    create(m1bicicleta: M1bicicleta): Observable<EntityResponseType> {
        const copy = this.convert(m1bicicleta);
        return this.http.post<M1bicicleta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(m1bicicleta: M1bicicleta): Observable<EntityResponseType> {
        const copy = this.convert(m1bicicleta);
        return this.http.put<M1bicicleta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<M1bicicleta>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<M1bicicleta[]>> {
        const options = createRequestOption(req);
        return this.http.get<M1bicicleta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<M1bicicleta[]>) => this.convertArrayResponse(res));
    }

    queryInService(req?: any): Observable<HttpResponse<M1bicicleta[]>> {
        const options = createRequestOption(req);
        return this.http.get<M1bicicleta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<M1bicicleta[]>) => this.convertArrayResponseInService(res));
    }

    queryRented(req?: any): Observable<HttpResponse<M1bicicleta[]>> {
        const options = createRequestOption(req);
        return this.http.get<M1bicicleta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<M1bicicleta[]>) => this.convertArrayResponseRented(res));
    }

    queryAvailable(req?: any): Observable<HttpResponse<M1bicicleta[]>> {
        const options = createRequestOption(req);
        return this.http.get<M1bicicleta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<M1bicicleta[]>) => this.convertArrayResponseAvailable(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: M1bicicleta = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<M1bicicleta[]>): HttpResponse<M1bicicleta[]> {
        const jsonResponse: M1bicicleta[] = res.body;
        const body: M1bicicleta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    private convertArrayResponseInService(res: HttpResponse<M1bicicleta[]>): HttpResponse<M1bicicleta[]> {
        const jsonResponse: M1bicicleta[] = res.body;
        const body: M1bicicleta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
        if ( jsonResponse[i].status === 3 ) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        }
        return res.clone({body});
    }

    private convertArrayResponseRented(res: HttpResponse<M1bicicleta[]>): HttpResponse<M1bicicleta[]> {
        const jsonResponse: M1bicicleta[] = res.body;
        const body: M1bicicleta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
        if ( jsonResponse[i].status === 2 ) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        }
        return res.clone({body});
    }

    private convertArrayResponseAvailable(res: HttpResponse<M1bicicleta[]>): HttpResponse<M1bicicleta[]> {
        const jsonResponse: M1bicicleta[] = res.body;
        const body: M1bicicleta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
        if ( jsonResponse[i].status === 1 ) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to M1bicicleta.
     */
    private convertItemFromServer(m1bicicleta: M1bicicleta): M1bicicleta {
        const copy: M1bicicleta = Object.assign({}, m1bicicleta);
        return copy;
    }

    /**
     * Convert a M1bicicleta to a JSON which can be sent to the server.
     */
    private convert(m1bicicleta: M1bicicleta): M1bicicleta {
        const copy: M1bicicleta = Object.assign({}, m1bicicleta);
        return copy;
    }
}
