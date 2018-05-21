/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { M4GatewayTestModule } from '../../../test.module';
import { BicicletaComponent } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta.component';
import { BicicletaService } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta.service';
import { Bicicleta } from '../../../../../../main/webapp/app/entities/bicicleta/bicicleta.model';

describe('Component Tests', () => {

    describe('Bicicleta Management Component', () => {
        let comp: BicicletaComponent;
        let fixture: ComponentFixture<BicicletaComponent>;
        let service: BicicletaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [M4GatewayTestModule],
                declarations: [BicicletaComponent],
                providers: [
                    BicicletaService
                ]
            })
            .overrideTemplate(BicicletaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BicicletaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BicicletaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bicicleta(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bicicletas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
