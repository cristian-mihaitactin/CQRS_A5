/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { M1bicicletaComponent } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.component';
import { M1bicicletaService } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.service';
import { M1bicicleta } from '../../../../../../main/webapp/app/entities/m-1-bicicleta/m-1-bicicleta.model';

describe('Component Tests', () => {

    describe('M1bicicleta Management Component', () => {
        let comp: M1bicicletaComponent;
        let fixture: ComponentFixture<M1bicicletaComponent>;
        let service: M1bicicletaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [M1bicicletaComponent],
                providers: [
                    M1bicicletaService
                ]
            })
            .overrideTemplate(M1bicicletaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(M1bicicletaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(M1bicicletaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new M1bicicleta(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.m1bicicletas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
