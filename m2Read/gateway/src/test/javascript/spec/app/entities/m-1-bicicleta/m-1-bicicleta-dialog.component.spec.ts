/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { M1bicicletaDialogComponent } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta-dialog.component';
import { M1bicicletaService } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.service';
import { M1bicicleta } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.model';

describe('Component Tests', () => {

    describe('M1bicicleta Management Dialog Component', () => {
        let comp: M1bicicletaDialogComponent;
        let fixture: ComponentFixture<M1bicicletaDialogComponent>;
        let service: M1bicicletaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [M1bicicletaDialogComponent],
                providers: [
                    M1bicicletaService
                ]
            })
            .overrideTemplate(M1bicicletaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(M1bicicletaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(M1bicicletaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new M1bicicleta(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.m1bicicleta = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'm1bicicletaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new M1bicicleta();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.m1bicicleta = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'm1bicicletaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
