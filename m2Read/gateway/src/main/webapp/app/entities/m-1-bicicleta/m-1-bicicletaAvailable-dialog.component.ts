import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { M1bicicleta } from './m-1-bicicleta.model';
import { M1bicicletaPopupService } from './m-1-bicicleta-popup.service';
import { M1bicicletaService } from './m-1-bicicleta.service';

@Component({
    selector: 'jhi-m-1-bicicleta-dialog',
    templateUrl: './m-1-bicicletaAvailable-dialog.component.html'
})
export class M1bicicletaAvailableDialogComponent implements OnInit {

    m1bicicleta: M1bicicleta;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private m1bicicletaService: M1bicicletaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.m1bicicleta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.m1bicicletaService.update(this.m1bicicleta));
        } else {
            this.subscribeToSaveResponse(
                this.m1bicicletaService.create(this.m1bicicleta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<M1bicicleta>>) {
        result.subscribe((res: HttpResponse<M1bicicleta>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: M1bicicleta) {
        this.eventManager.broadcast({ name: 'm1bicicletaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-m-1-bicicleta-popup',
    template: ''
})
export class M1bicicletaAvailablePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private m1bicicletaPopupService: M1bicicletaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.m1bicicletaPopupService
                    .open(M1bicicletaAvailableDialogComponent as Component, params['id']);
            } else {
                this.m1bicicletaPopupService
                    .open(M1bicicletaAvailableDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
