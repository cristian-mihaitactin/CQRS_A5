import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    M1bicicletaService,
    M1bicicletaPopupService,
    M1bicicletaComponent,
    M1bicicletaInServiceComponent,
    M1bicicletaRentedComponent,
    M1bicicletaAvailableComponent,
    M1bicicletaDetailComponent,
    M1bicicletaDialogComponent,
    M1bicicletaPopupComponent,
    M1bicicletaInServiceDialogComponent,
    M1bicicletaInServicePopupComponent,
    M1bicicletaRentedDialogComponent,
    M1bicicletaRentedPopupComponent,
    M1bicicletaAvailableDialogComponent,
    M1bicicletaAvailablePopupComponent,
    M1bicicletaDeletePopupComponent,
    M1bicicletaDeleteDialogComponent,
    m1bicicletaRoute,
    m1bicicletaPopupRoute,
    M1bicicletaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...m1bicicletaRoute,
    ...m1bicicletaPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        M1bicicletaComponent,
        M1bicicletaInServiceComponent,
        M1bicicletaRentedComponent,
        M1bicicletaAvailableComponent,
        M1bicicletaDetailComponent,
        M1bicicletaDialogComponent,
        M1bicicletaInServiceDialogComponent,
        M1bicicletaInServicePopupComponent,
        M1bicicletaRentedDialogComponent,
        M1bicicletaRentedPopupComponent,
        M1bicicletaAvailableDialogComponent,
        M1bicicletaAvailablePopupComponent,
        M1bicicletaDeleteDialogComponent,
        M1bicicletaPopupComponent,
        M1bicicletaDeletePopupComponent,
    ],
    entryComponents: [
        M1bicicletaComponent,
        M1bicicletaInServiceComponent,
        M1bicicletaRentedComponent,
        M1bicicletaAvailableComponent,
        M1bicicletaDialogComponent,
        M1bicicletaInServiceDialogComponent,
        M1bicicletaInServicePopupComponent,
        M1bicicletaRentedDialogComponent,
        M1bicicletaRentedPopupComponent,
        M1bicicletaAvailableDialogComponent,
        M1bicicletaAvailablePopupComponent,
        M1bicicletaPopupComponent,
        M1bicicletaDeleteDialogComponent,
        M1bicicletaDeletePopupComponent,
    ],
    providers: [
        M1bicicletaService,
        M1bicicletaPopupService,
        M1bicicletaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayM1bicicletaModule {}
