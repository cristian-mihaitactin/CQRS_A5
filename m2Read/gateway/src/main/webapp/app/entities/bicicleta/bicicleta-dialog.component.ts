import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bicicleta } from './bicicleta.model';
import { BicicletaPopupService } from './bicicleta-popup.service';
import { BicicletaService } from './bicicleta.service';

@Component({
    selector: 'jhi-bicicleta-dialog',
    templateUrl: './bicicleta-dialog.component.html'
})
export class BicicletaDialogComponent implements OnInit {

    bicicleta: Bicicleta;
    isSaving: boolean;
    data_inchiriereDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private bicicletaService: BicicletaService,
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
        if (this.bicicleta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bicicletaService.update(this.bicicleta));
        } else {
            this.subscribeToSaveResponse(
                this.bicicletaService.create(this.bicicleta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bicicleta>>) {
        result.subscribe((res: HttpResponse<Bicicleta>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bicicleta) {
        this.eventManager.broadcast({ name: 'bicicletaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-bicicleta-popup',
    template: ''
})
export class BicicletaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bicicletaPopupService: BicicletaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bicicletaPopupService
                    .open(BicicletaDialogComponent as Component, params['id']);
            } else {
                this.bicicletaPopupService
                    .open(BicicletaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
