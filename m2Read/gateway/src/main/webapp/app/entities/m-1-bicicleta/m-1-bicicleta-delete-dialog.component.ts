import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { M1bicicleta } from './m-1-bicicleta.model';
import { M1bicicletaPopupService } from './m-1-bicicleta-popup.service';
import { M1bicicletaService } from './m-1-bicicleta.service';

@Component({
    selector: 'jhi-m-1-bicicleta-delete-dialog',
    templateUrl: './m-1-bicicleta-delete-dialog.component.html'
})
export class M1bicicletaDeleteDialogComponent {

    m1bicicleta: M1bicicleta;

    constructor(
        private m1bicicletaService: M1bicicletaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.m1bicicletaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'm1bicicletaListModification',
                content: 'Deleted an m1bicicleta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-m-1-bicicleta-delete-popup',
    template: ''
})
export class M1bicicletaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private m1bicicletaPopupService: M1bicicletaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.m1bicicletaPopupService
                .open(M1bicicletaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
