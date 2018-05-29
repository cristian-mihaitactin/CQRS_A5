import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Ordin } from './ordin.model';
import { OrdinService } from './ordin.service';
import { letProto } from 'rxjs/operator/let';

//import { M1bicicleta } from './../m-1-bicicleta/m-1-bicicleta.model';
//import { M1bicicletaService } from './../m-1-bicicleta/m-1-bicicleta.service';

@Injectable()
export class OrdinPopupService {
    private ngbModalRef: NgbModalRef;
    //private m1bicicleta: M1bicicleta;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ordinService: OrdinService,
        //private m1bicicletaService: M1bicicletaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                if( id.indexOf('bikeId:') >= 0){
                    let bikeId = id.split('bikeId:',2)[1];
                    setTimeout(() => {
                        let ordn = new Ordin();
                        ordn.id_bike = bikeId;
                        this.ngbModalRef = this.ordinModalRef(component, ordn);
                        resolve(this.ngbModalRef);
                    }, 0);
                    //this.toRented(bikeId);
                  }
                this.ordinService.find(id)
                    .subscribe((ordinResponse: HttpResponse<Ordin>) => {
                        const ordin: Ordin = ordinResponse.body;
                        if (ordin.data_inchiriere) {
                            ordin.data_inchiriere = {
                                year: ordin.data_inchiriere.getFullYear(),
                                month: ordin.data_inchiriere.getMonth() + 1,
                                day: ordin.data_inchiriere.getDate()
                            };
                        }
                        this.ngbModalRef = this.ordinModalRef(component, ordin);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ordinModalRef(component, new Ordin());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ordinModalRef(component: Component, ordin: Ordin): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ordin = ordin;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
