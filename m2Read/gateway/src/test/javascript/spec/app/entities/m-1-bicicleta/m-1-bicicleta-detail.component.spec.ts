/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { M1bicicletaDetailComponent } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta-detail.component';
import { M1bicicletaService } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.service';
import { M1bicicleta } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.model';

describe('Component Tests', () => {

    describe('M1bicicleta Management Detail Component', () => {
        let comp: M1bicicletaDetailComponent;
        let fixture: ComponentFixture<M1bicicletaDetailComponent>;
        let service: M1bicicletaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [M1bicicletaDetailComponent],
                providers: [
                    M1bicicletaService
                ]
            })
            .overrideTemplate(M1bicicletaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(M1bicicletaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(M1bicicletaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new M1bicicleta(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.m1bicicleta).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
