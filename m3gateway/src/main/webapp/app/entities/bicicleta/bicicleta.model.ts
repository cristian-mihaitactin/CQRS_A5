import { BaseEntity } from './../../shared';

export class Bicicleta implements BaseEntity {
    constructor(
        public id?: number,
        public serie?: string,
        public pret?: number,
        public data_inchiriere?: any,
        public timp_inchiriere?: number,
        public data_return?: any,
        public status?: number,
        public cnp_renter?: string,
    ) {
    }
}
