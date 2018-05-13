import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BikeComponent } from './bike.component';
import { BikeDetailComponent } from './bike-detail.component';
import { BikePopupComponent } from './bike-dialog.component';
import { BikeDeletePopupComponent } from './bike-delete-dialog.component';

@Injectable()
export class BikeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const bikeRoute: Routes = [
    {
        path: 'bike',
        component: BikeComponent,
        resolve: {
            'pagingParams': BikeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bikes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bike/:id',
        component: BikeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bikes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bikePopupRoute: Routes = [
    {
        path: 'bike-new',
        component: BikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bikes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bike/:id/edit',
        component: BikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bikes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bike/:id/delete',
        component: BikeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bikes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
