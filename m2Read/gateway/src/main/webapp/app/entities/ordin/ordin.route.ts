import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OrdinComponent } from './ordin.component';
import { OrdinDetailComponent } from './ordin-detail.component';
import { OrdinPopupComponent } from './ordin-dialog.component';
import { OrdinDeletePopupComponent } from './ordin-delete-dialog.component';

@Injectable()
export class OrdinResolvePagingParams implements Resolve<any> {

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

export const ordinRoute: Routes = [
    {
        path: 'ordin',
        component: OrdinComponent,
        resolve: {
            'pagingParams': OrdinResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordins'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ordin/:id',
        component: OrdinDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordins'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordinPopupRoute: Routes = [
    {
        path: 'ordin-new',
        component: OrdinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordin/:id/edit',
        component: OrdinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ordin/:id/delete',
        component: OrdinDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
