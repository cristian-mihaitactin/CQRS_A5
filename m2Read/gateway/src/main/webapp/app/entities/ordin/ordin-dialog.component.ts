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

import { M1bicicleta } from './../m-1-bicicleta/m-1-bicicleta.model';
import { M1bicicletaService } from './../m-1-bicicleta/m-1-bicicleta.service';

@Component({
    selector: 'jhi-ordin-dialog',
    templateUrl: './ordin-dialog.component.html'
})
export class OrdinDialogComponent implements OnInit {

    ordin: Ordin;
    isSaving: boolean;
    data_inchiriereDp: any;
    m1bicicleta: M1bicicleta;
    constructor(
        public activeModal: NgbActiveModal,
        private ordinService: OrdinService,
        private eventManager: JhiEventManager,
        private m1bicicletaService: M1bicicletaService
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

            this.m1bicicletaService.find(this.ordin.id_bike)
                .subscribe((m1bicicletaResponse: HttpResponse<M1bicicleta>) => {
                    this.m1bicicleta = m1bicicletaResponse.body;
            });
        
            console.log("Bicicleta id:" + this.ordin.id_bike);
            console.log(this.m1bicicleta);
            
            //update status
            console.log("to Rent call");
            if(this.m1bicicleta == 1)
            {
                this.m1bicicleta.status = 2;
                //update bike
                this.m1bicicletaService.update(this.m1bicicleta).subscribe((response) => {
            });
            }
            
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
