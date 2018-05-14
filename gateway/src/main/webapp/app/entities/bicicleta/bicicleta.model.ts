import { BaseEntity } from './../../shared';

export class Bicicleta implements BaseEntity {
    constructor(
        public id?: number,
        public serie?: string,
        public stare?: number,
    ) {
    }
}
