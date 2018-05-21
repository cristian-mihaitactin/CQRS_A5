import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Bicicleta } from './bicicleta.model';
import { BicicletaService } from './bicicleta.service';

@Injectable()
export class BicicletaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private bicicletaService: BicicletaService

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
                this.bicicletaService.find(id)
                    .subscribe((bicicletaResponse: HttpResponse<Bicicleta>) => {
                        const bicicleta: Bicicleta = bicicletaResponse.body;
                        if (bicicleta.data_inchiriere) {
                            bicicleta.data_inchiriere = {
                                year: bicicleta.data_inchiriere.getFullYear(),
                                month: bicicleta.data_inchiriere.getMonth() + 1,
                                day: bicicleta.data_inchiriere.getDate()
                            };
                        }
                        if (bicicleta.data_return) {
                            bicicleta.data_return = {
                                year: bicicleta.data_return.getFullYear(),
                                month: bicicleta.data_return.getMonth() + 1,
                                day: bicicleta.data_return.getDate()
                            };
                        }
                        this.ngbModalRef = this.bicicletaModalRef(component, bicicleta);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.bicicletaModalRef(component, new Bicicleta());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    bicicletaModalRef(component: Component, bicicleta: Bicicleta): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bicicleta = bicicleta;
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
