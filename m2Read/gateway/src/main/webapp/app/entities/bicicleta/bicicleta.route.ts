import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BicicletaComponent } from './bicicleta.component';
import { BicicletaDetailComponent } from './bicicleta-detail.component';
import { BicicletaPopupComponent } from './bicicleta-dialog.component';
import { BicicletaDeletePopupComponent } from './bicicleta-delete-dialog.component';

//experiment
import { OrdinPopupComponent } from './../ordin/ordin-dialog.component';
/////////////
@Injectable()
export class BicicletaResolvePagingParams implements Resolve<any> {

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

export const bicicletaRoute: Routes = [
    {
        path: 'bicicleta',
        component: BicicletaComponent,
        resolve: {
            'pagingParams': BicicletaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bicicletas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bicicleta/:id',
        component: BicicletaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bicicletas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bicicletaPopupRoute: Routes = [
    {
        path: 'bicicleta-new',
        component: BicicletaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bicicleta/:id/edit',
        component: BicicletaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bicicleta/:id/delete',
        component: BicicletaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bicicletas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

export const toOrderRoute: Routes = [
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
];