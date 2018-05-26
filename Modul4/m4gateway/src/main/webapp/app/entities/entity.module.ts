import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { M4GatewayBicicletaModule } from './bicicleta/bicicleta.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        M4GatewayBicicletaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class M4GatewayEntityModule {}
