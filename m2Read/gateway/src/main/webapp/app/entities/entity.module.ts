import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayBicicletaModule } from './bicicleta/bicicleta.module';
import { GatewayOrdinModule } from './ordin/ordin.module';
import { GatewayM1bicicletaModule } from './m-1-bicicleta/m-1-bicicleta.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayBicicletaModule,
        GatewayOrdinModule,
        GatewayM1bicicletaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
