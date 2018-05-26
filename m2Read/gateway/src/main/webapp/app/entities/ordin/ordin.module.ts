import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    OrdinService,
    OrdinPopupService,
    OrdinComponent,
    OrdinDetailComponent,
    OrdinDialogComponent,
    OrdinPopupComponent,
    OrdinDeletePopupComponent,
    OrdinDeleteDialogComponent,
    ordinRoute,
    ordinPopupRoute,
    OrdinResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ordinRoute,
    ...ordinPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OrdinComponent,
        OrdinDetailComponent,
        OrdinDialogComponent,
        OrdinDeleteDialogComponent,
        OrdinPopupComponent,
        OrdinDeletePopupComponent,
    ],
    entryComponents: [
        OrdinComponent,
        OrdinDialogComponent,
        OrdinPopupComponent,
        OrdinDeleteDialogComponent,
        OrdinDeletePopupComponent,
    ],
    providers: [
        OrdinService,
        OrdinPopupService,
        OrdinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayOrdinModule {}
