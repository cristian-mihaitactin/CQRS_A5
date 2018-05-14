import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bike } from './bike.model';
import { BikeService } from './bike.service';

@Component({
    selector: 'jhi-bike-detail',
    templateUrl: './bike-detail.component.html'
})
export class BikeDetailComponent implements OnInit, OnDestroy {

    bike: Bike;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bikeService: BikeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBikes();
    }

    load(id) {
        this.bikeService.find(id)
            .subscribe((bikeResponse: HttpResponse<Bike>) => {
                this.bike = bikeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBikes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bikeListModification',
            (response) => this.load(this.bike.id)
        );
    }
}
