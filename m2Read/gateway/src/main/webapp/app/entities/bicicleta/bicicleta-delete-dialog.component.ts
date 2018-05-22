import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bicicleta } from './bicicleta.model';
import { BicicletaPopupService } from './bicicleta-popup.service';
import { BicicletaService } from './bicicleta.service';

@Component({
    selector: 'jhi-bicicleta-delete-dialog',
    templateUrl: './bicicleta-delete-dialog.component.html'
})
export class BicicletaDeleteDialogComponent {

    bicicleta: Bicicleta;

    constructor(
        private bicicletaService: BicicletaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bicicletaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bicicletaListModification',
                content: 'Deleted an bicicleta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bicicleta-delete-popup',
    template: ''
})
export class BicicletaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bicicletaPopupService: BicicletaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bicicletaPopupService
                .open(BicicletaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
