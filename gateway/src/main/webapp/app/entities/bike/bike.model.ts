import { BaseEntity } from './../../shared';

export class Bike implements BaseEntity {
    constructor(
        public id?: number,
        public model?: string,
        public price?: number,
        public manufacturer?: string,
    ) {
    }
}
