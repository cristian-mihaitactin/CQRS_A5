import { BaseEntity } from './../../shared';

export class Ordin implements BaseEntity {
    constructor(
        public id?: number,
        public serie_user?: string,
        public data_inchiriere?: any,
        public id_bike?: number,
    ) {
    }
}
