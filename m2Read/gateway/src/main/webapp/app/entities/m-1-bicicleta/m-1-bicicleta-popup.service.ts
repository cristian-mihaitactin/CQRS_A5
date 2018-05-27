import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { M1bicicleta } from './m-1-bicicleta.model';
import { M1bicicletaService } from './m-1-bicicleta.service';

@Injectable()
export class M1bicicletaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private m1bicicletaService: M1bicicletaService

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
                this.m1bicicletaService.find(id)
                    .subscribe((m1bicicletaResponse: HttpResponse<M1bicicleta>) => {
                        const m1bicicleta: M1bicicleta = m1bicicletaResponse.body;
                        this.ngbModalRef = this.m1bicicletaModalRef(component, m1bicicleta);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.m1bicicletaModalRef(component, new M1bicicleta());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    m1bicicletaModalRef(component: Component, m1bicicleta: M1bicicleta): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.m1bicicleta = m1bicicleta;
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
