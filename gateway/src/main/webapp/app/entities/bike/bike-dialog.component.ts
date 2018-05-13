import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bike } from './bike.model';
import { BikePopupService } from './bike-popup.service';
import { BikeService } from './bike.service';

@Component({
    selector: 'jhi-bike-dialog',
    templateUrl: './bike-dialog.component.html'
})
export class BikeDialogComponent implements OnInit {

    bike: Bike;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bikeService: BikeService,
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
        if (this.bike.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bikeService.update(this.bike));
        } else {
            this.subscribeToSaveResponse(
                this.bikeService.create(this.bike));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bike>>) {
        result.subscribe((res: HttpResponse<Bike>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bike) {
        this.eventManager.broadcast({ name: 'bikeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-bike-popup',
    template: ''
})
export class BikePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bikePopupService: BikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bikePopupService
                    .open(BikeDialogComponent as Component, params['id']);
            } else {
                this.bikePopupService
                    .open(BikeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
