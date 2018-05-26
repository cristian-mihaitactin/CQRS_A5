import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Ordin } from './ordin.model';
import { OrdinPopupService } from './ordin-popup.service';
import { OrdinService } from './ordin.service';

@Component({
    selector: 'jhi-ordin-delete-dialog',
    templateUrl: './ordin-delete-dialog.component.html'
})
export class OrdinDeleteDialogComponent {

    ordin: Ordin;

    constructor(
        private ordinService: OrdinService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ordinListModification',
                content: 'Deleted an ordin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordin-delete-popup',
    template: ''
})
export class OrdinDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ordinPopupService: OrdinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ordinPopupService
                .open(OrdinDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
