import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bike } from './bike.model';
import { BikePopupService } from './bike-popup.service';
import { BikeService } from './bike.service';

@Component({
    selector: 'jhi-bike-delete-dialog',
    templateUrl: './bike-delete-dialog.component.html'
})
export class BikeDeleteDialogComponent {

    bike: Bike;

    constructor(
        private bikeService: BikeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bikeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bikeListModification',
                content: 'Deleted an bike'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bike-delete-popup',
    template: ''
})
export class BikeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bikePopupService: BikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bikePopupService
                .open(BikeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
