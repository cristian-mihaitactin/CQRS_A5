import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ordin } from './ordin.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ordin>;

@Injectable()
export class OrdinService {

    private resourceUrl =  SERVER_API_URL + 'm3order/api/ordins';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(ordin: Ordin): Observable<EntityResponseType> {
        const copy = this.convert(ordin);
        return this.http.post<Ordin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ordin: Ordin): Observable<EntityResponseType> {
        const copy = this.convert(ordin);
        return this.http.put<Ordin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Ordin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Ordin[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ordin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ordin[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ordin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ordin[]>): HttpResponse<Ordin[]> {
        const jsonResponse: Ordin[] = res.body;
        const body: Ordin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Ordin.
     */
    private convertItemFromServer(ordin: Ordin): Ordin {
        const copy: Ordin = Object.assign({}, ordin);
        copy.data_inchiriere = this.dateUtils
            .convertLocalDateFromServer(ordin.data_inchiriere);
        return copy;
    }

    /**
     * Convert a Ordin to a JSON which can be sent to the server.
     */
    private convert(ordin: Ordin): Ordin {
        const copy: Ordin = Object.assign({}, ordin);
        copy.data_inchiriere = this.dateUtils
            .convertLocalDateToServer(ordin.data_inchiriere);
        return copy;
    }
}
