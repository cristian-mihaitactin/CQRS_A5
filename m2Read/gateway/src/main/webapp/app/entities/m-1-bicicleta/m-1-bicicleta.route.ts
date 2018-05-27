import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { M1bicicletaComponent } from './m-1-bicicleta.component';
import { M1bicicletaInServiceComponent } from './m-1-bicicletaInService.component';
import { M1bicicletaRentedComponent } from './m-1-bicicletaRented.component';
import { M1bicicletaAvailableComponent } from './m-1-bicicletaAvailable.component';
import { M1bicicletaDetailComponent } from './m-1-bicicleta-detail.component';
import { M1bicicletaPopupComponent } from './m-1-bicicleta-dialog.component';
import { M1bicicletaInServicePopupComponent } from './m-1-bicicletaInService-dialog.component';
import { M1bicicletaInServiceDialogComponent } from './m-1-bicicletaInService-dialog.component';
import { M1bicicletaRentedPopupComponent } from './m-1-bicicletaRented-dialog.component';
import { M1bicicletaRentedDialogComponent } from './m-1-bicicletaRented-dialog.component';
import { M1bicicletaAvailablePopupComponent } from './m-1-bicicletaAvailable-dialog.component';
import { M1bicicletaAvailableDialogComponent } from './m-1-bicicletaAvailable-dialog.component';
import { M1bicicletaDeletePopupComponent } from './m-1-bicicleta-delete-dialog.component';

@Injectable()
export class M1bicicletaResolvePagingParams implements Resolve<any> {

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

export const m1bicicletaRoute: Routes = [
    {
        path: 'm-1-bicicleta',
        component: M1bicicletaComponent,
        resolve: {
            'pagingParams': M1bicicletaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'm-1-bicicleta/:id',
        component: M1bicicletaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'm-1-bicicletaInService',
        component: M1bicicletaInServiceComponent,
        resolve: {
            'pagingParams': M1bicicletaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biciclete in reparare'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'm-1-bicicletaRented',
        component: M1bicicletaRentedComponent,
        resolve: {
            'pagingParams': M1bicicletaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biciclete inchiriate'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'm-1-bicicletaAvailable',
        component: M1bicicletaAvailableComponent,
        resolve: {
            'pagingParams': M1bicicletaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biciclete valabile'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const m1bicicletaPopupRoute: Routes = [
    {
        path: 'm-1-bicicleta-new',
        component: M1bicicletaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'm-1-bicicleta/:id/edit',
        component: M1bicicletaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'm-1-bicicletaInService/:id/edit',
        component: M1bicicletaInServicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'm-1-bicicletaRented/:id/edit',
        component: M1bicicletaRentedPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'm-1-bicicletaAvailable/:id/edit',
        component: M1bicicletaAvailablePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'm-1-bicicleta/:id/delete',
        component: M1bicicletaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'M1bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
