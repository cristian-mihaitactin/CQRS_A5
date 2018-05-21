import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    BicicletaService,
    BicicletaPopupService,
    BicicletaComponent,
    BicicletaDetailComponent,
    BicicletaDialogComponent,
    BicicletaPopupComponent,
    BicicletaDeletePopupComponent,
    BicicletaDeleteDialogComponent,
    bicicletaRoute,
    bicicletaPopupRoute,
    BicicletaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bicicletaRoute,
    ...bicicletaPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BicicletaComponent,
        BicicletaDetailComponent,
        BicicletaDialogComponent,
        BicicletaDeleteDialogComponent,
        BicicletaPopupComponent,
        BicicletaDeletePopupComponent,
    ],
    entryComponents: [
        BicicletaComponent,
        BicicletaDialogComponent,
        BicicletaPopupComponent,
        BicicletaDeleteDialogComponent,
        BicicletaDeletePopupComponent,
    ],
    providers: [
        BicicletaService,
        BicicletaPopupService,
        BicicletaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayBicicletaModule {}
