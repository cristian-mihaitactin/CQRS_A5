import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { M1bicicleta } from './m-1-bicicleta.model';
import { M1bicicletaService } from './m-1-bicicleta.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
@Component({
    selector: 'jhi-m-1-bicicleta',
    templateUrl: './m-1-bicicletaAvailable.component.html'
})
export class M1bicicletaAvailableComponent implements OnInit, OnDestroy {

currentAccount: any;
    m1bicicletas: M1bicicleta[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private m1bicicletaService: M1bicicletaService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.m1bicicletaService.queryAvailable({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<M1bicicleta[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/m-1-bicicleta'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/m-1-bicicleta', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInM1bicicletas();
    }
    
    // confirmDelete(m1bicicleta: M1bicicleta) {
    //     this.m1bicicletaService.update(m1bicicleta).subscribe((response) => {
    //         this.eventManager.broadcast({
    //             name: 'm1bicicletaListModification',
    //             content: 'Moved an m1bicicleta to repair'
    //         });
    //     });
    // }
   

    toRepair(m1bicicleta: M1bicicleta) {
        console.log("toRepair click");
        m1bicicleta.status = 3;
        console.log(m1bicicleta);
        this.m1bicicletaService.update(m1bicicleta).subscribe((response) => {
            this.loadAll();
        });
    }
    

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: M1bicicleta) {
        return item.id;
    }
    registerChangeInM1bicicletas() {
        this.eventSubscriber = this.eventManager.subscribe('m1bicicletaListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.m1bicicletas = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
