import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    BikeService,
    BikePopupService,
    BikeComponent,
    BikeDetailComponent,
    BikeDialogComponent,
    BikePopupComponent,
    BikeDeletePopupComponent,
    BikeDeleteDialogComponent,
    bikeRoute,
    bikePopupRoute,
    BikeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bikeRoute,
    ...bikePopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BikeComponent,
        BikeDetailComponent,
        BikeDialogComponent,
        BikeDeleteDialogComponent,
        BikePopupComponent,
        BikeDeletePopupComponent,
    ],
    entryComponents: [
        BikeComponent,
        BikeDialogComponent,
        BikePopupComponent,
        BikeDeleteDialogComponent,
        BikeDeletePopupComponent,
    ],
    providers: [
        BikeService,
        BikePopupService,
        BikeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayBikeModule {}
