import { BaseEntity } from './../../shared';

export class Bicicleta implements BaseEntity {
    constructor(
        public id?: number,
        public serie?: string,
        public pret?: number,
        public tip?: string,
        public model?: string,
        public anFabricatie?: number,
        public status?: number,
        public descriere?: string,
    ) {
    }
}
