import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Ordin } from './ordin.model';
import { OrdinService } from './ordin.service';

@Component({
    selector: 'jhi-ordin-detail',
    templateUrl: './ordin-detail.component.html'
})
export class OrdinDetailComponent implements OnInit, OnDestroy {

    ordin: Ordin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ordinService: OrdinService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrdins();
    }

    load(id) {
        this.ordinService.find(id)
            .subscribe((ordinResponse: HttpResponse<Ordin>) => {
                this.ordin = ordinResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrdins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ordinListModification',
            (response) => this.load(this.ordin.id)
        );
    }
}
