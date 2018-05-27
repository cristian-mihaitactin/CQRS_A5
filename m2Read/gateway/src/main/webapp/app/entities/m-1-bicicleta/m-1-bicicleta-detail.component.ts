import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { M1bicicleta } from './m-1-bicicleta.model';
import { M1bicicletaService } from './m-1-bicicleta.service';

@Component({
    selector: 'jhi-m-1-bicicleta-detail',
    templateUrl: './m-1-bicicleta-detail.component.html'
})
export class M1bicicletaDetailComponent implements OnInit, OnDestroy {

    m1bicicleta: M1bicicleta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private m1bicicletaService: M1bicicletaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInM1bicicletas();
    }

    load(id) {
        this.m1bicicletaService.find(id)
            .subscribe((m1bicicletaResponse: HttpResponse<M1bicicleta>) => {
                this.m1bicicleta = m1bicicletaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInM1bicicletas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'm1bicicletaListModification',
            (response) => this.load(this.m1bicicleta.id)
        );
    }
}
