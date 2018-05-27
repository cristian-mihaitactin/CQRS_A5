import { BaseEntity } from './../../shared';

export class M1bicicleta implements BaseEntity {
    constructor(
        public id?: number,
        public serie?: string,
        public model?: string,
        public tip?: string,
        public pret?: number,
        public descriere?: string,
        public anFabricatie?: number,
        public status?: number,
    ) {
    }
}
