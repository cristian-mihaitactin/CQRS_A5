import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ordin } from './ordin.model';
import { OrdinPopupService } from './ordin-popup.service';
import { OrdinService } from './ordin.service';
import { concat } from 'rxjs/operator/concat';

@Component({
    selector: 'jhi-ordin-dialog',
    templateUrl: './ordin-dialog.component.html'
})
export class OrdinDialogComponent implements OnInit {

    ordin: Ordin;
    isSaving: boolean;
    data_inchiriereDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private ordinService: OrdinService,
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
        if (this.ordin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ordinService.update(this.ordin));
        } else {
            this.subscribeToSaveResponse(
                this.ordinService.create(this.ordin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Ordin>>) {
        result.subscribe((res: HttpResponse<Ordin>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Ordin) {
        this.eventManager.broadcast({ name: 'ordinListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-ordin-popup',
    template: ''
})
export class OrdinPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordinPopupService: OrdinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ordinPopupService
                    .open(OrdinDialogComponent as Component, params['id']);

            }else if (params['bikeId']){
                try 
                {
                    this.ordinPopupService
                    .open(OrdinDialogComponent as Component, 'bikeId:' + params['bikeId']);
                }catch(error) {
                    console.error(error);
                    // expected output: SyntaxError: unterminated string literal
                    // Note - error messages will vary depending on browser
                  }
                

            } else {
                this.ordinPopupService
                    .open(OrdinDialogComponent as Component);

            }
        });
            }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
