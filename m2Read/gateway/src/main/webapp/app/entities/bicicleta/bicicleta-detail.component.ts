import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bicicleta } from './bicicleta.model';
import { BicicletaService } from './bicicleta.service';

@Component({
    selector: 'jhi-bicicleta-detail',
    templateUrl: './bicicleta-detail.component.html'
})
export class BicicletaDetailComponent implements OnInit, OnDestroy {

    bicicleta: Bicicleta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bicicletaService: BicicletaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBicicletas();
    }

    load(id) {
        this.bicicletaService.find(id)
            .subscribe((bicicletaResponse: HttpResponse<Bicicleta>) => {
                this.bicicleta = bicicletaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBicicletas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bicicletaListModification',
            (response) => this.load(this.bicicleta.id)
        );
    }
}
